#  – Project guide

## 1. Architecture & Multi-Module Structure

Applying Clean Architecture principles with a multi-module Gradle setup for modular and scalable applications.

* **Multi-Module Gradle Structure:**
    * Use **Gradle multi-module projects** to enforce clear architectural boundaries and enable independent development of features.
    * **Application Module (`bootstrap/app`):** The main entry point that assembles all feature modules, contains the main Spring Boot application class, and defines the overall application configuration.
    * **Feature Modules:** Each major feature or bounded context should be its own Gradle module (e.g., `user-management`, `product-catalog`, `order-processing`). Feature modules should encapsulate all related code, including API, application, domain, and infrastructure layers.
    * **Feature Database:** Database migrations and schema management is handled in a dedicated module `database` using Liquibase.
    * Example project structure with feature module user-management:
        ```
        {{ cookiecutter.project_name }}/
        ├── settings.gradle
        ├── build.gradle
        ├── bootstrap/                                                  // Main application module
        |   └── app/
        │       ├── build.gradle
        │       └── src
        │           ├── main
        |           |   ├── java/
        │           |       └── {{ cookiecutter.package_dir }}/app/
        │           |           └── Application.java                                // @SpringBootApplication
        |           ├── test                                                        // Integration tests
        │           |   └── groovy/                                                 
        │           |       └── {{ cookiecutter.package_dir }}/app/            
        │           |           └── AppBaseSpecification.groovy                     // Base specification for integration tests
        │           └── resources/          
        │               └── application.yml                                         // Application configuration
        └── features/           
            ├── database/                                                           // Application database migrations module
            |   ├── build.gradle            
            |   └── src/main/resources          
            |       ├── changelog.xml                                               // Liquibase master changelog file
            |       └── changesets/                                                 // Changesets directory 
            └── user-management/                                                    // Feature module
                ├── build.gradle
                └── src/main/java/{{ cookiecutter.package_dir }}/usermanagement/
                    ├── api/                                                        // REST controllers
                    ├── application/                                                // Use cases (application services)
                    ├── domain/                                                     // Domain layer
                    |   ├── model/                                                  // Domain models
                    |   └── ports/                                                  // Domain service interfaces (ports)
                    └── infrastructure/                                             // External adapters
        ```

* **Clean Architecture / Hexagonal Architecture:**
    * Strictly apply the principles of **Clean Architecture** (also known as Hexagonal Architecture or Ports and Adapters) within each feature module.
    * **Domain Layer:** Contains pure business logic (entities, value objects, domain services, interfaces defining ports). It has no dependencies on outer layers.
    * **Infrastructure Layer:** Contains concrete implementations of interfaces defined in the Application or Domain layers. This includes persistence (JPA repositories, custom DAOs), external REST clients, message queue clients, etc. Depends on Application and Domain Layers.
    * **Application Layer:** Defines application-specific use cases, orchestrating domain logic. It contains service interfaces (ports) and their implementations (use case interactors). Depends on the Domain Layer.
    * **API Layer:** Contains entry points into the application, such as REST controllers, WebSockets handlers, or CLI commands. Adapts external requests into use case calls. Depends on Application Layer.

* **Service Definitions:**
    * Always define **interfaces for service definitions** (ports) in the Domain layer within each feature module. Implementations (adapters) belong in the Infrastructure layer.
    * For cross-feature communication, use use cases defined in the application layer.

* **Adding New Feature Modules:**
    * Use the provided Gradle task to scaffold new feature modules with the correct structure and configuration:
        ```bash
        ./gradlew addFeature --featureName=<feature_name>
        ```
    * Replace `<feature_name>` with the desired name for your feature (e.g., `user`, `order`, `payment`)
    * This task will automatically:
        * Create the feature module under `features/<feature_name>`
        * Set up the recommended package structure with `api/`, `application/`, `domain/`, and `infrastructure/` directories
        * Register the module in `settings.gradle`
        * Add the dependency to `bootstrap/app/build.gradle`
    * After running the task, you can start implementing your feature's domain, application, API, and infrastructure code in the generated directories
    * The scaffolded feature will follow the Clean Architecture pattern with proper layer separation

## 2. Database Migrations

Database schema changes are managed using Liquibase in the dedicated `features/database` module.

* **Migration File Location:**
    * All migration files should be placed in `features/database/src/main/resources/changesets/`
    * Use Liquibase SQL format for migration files

* **Migration File Naming Convention:**
    * Format: `YYYY_MM_DD-description.sql`
    * Date should be the current date when creating the migration
    * Description should be a short, kebab-case description of the change
    * Examples:
        * `2025_08_24-add-users-table.sql`
        * `2025_08_24-add-email-index-to-users.sql`
        * `2025_08_25-create-orders-table.sql`

* **Updating Master Changelog:**
    * After creating a new migration file, update `features/database/src/main/resources/changelog.xml`
    * Add an `<include>` entry for the new changeset file
    * Example:
        ```xml
        <include file="changesets/2025_08_24-add-users-table.sql" relativeToChangelogFile="true"/>
        ```

* **Migration File Structure:**
    * Start each migration file with Liquibase metadata:
        ```sql
        --liquibase formatted sql

        --changeset author:unique-changeset-id
        -- Description of the change

        -- Your SQL statements here
        ```
    * Use descriptive changeset IDs that include the date and purpose
    * Always include rollback statements when possible

## 3. Testing Strategy

Testing is critical for maintaining code quality and ensuring the reliability of the modular monolith. We prioritize integration tests over unit tests to validate the complete behavior of features.

* **Test-First Development:**
    * **Always start with tests** when adding a new feature or endpoint
    * Write integration tests first to define the expected behavior before implementing the feature
    * This ensures clear requirements, better design decisions, and prevents over-engineering
    * Follow the Red-Green-Refactor cycle: write failing tests, implement minimal code to pass, then refactor

* **Integration Tests over Unit Tests:**
    * **Prefer integration tests** that test the complete behavior of a feature, including database interactions, HTTP endpoints, and business logic.
    * Unit tests should only be used when there's no feasible way to test certain use cases through integration tests (e.g., complex domain logic in isolation, edge cases in utility methods).
    * Integration tests provide better confidence in the system's behavior and catch issues that unit tests might miss.

* **Test Location and Structure:**
    * All integration tests should be located in: `bootstrap/app/src/test/groovy/{{cookiecutter.package_dir}}/app/`
    * Use **Groovy and Spock** framework for all integration tests
    * Integration tests should inherit from `AppBaseSpecification.groovy` for common test setup and utilities

* **Test Data Management:**
    * Create JSON files for request/response data in: `bootstrap/app/src/test/resources/`
    * Organize test data files by feature or endpoint
    * Use descriptive naming for JSON files (e.g., `create-user-request.json`, `user-created-response.json`)
    * Example structure:
        ```
        bootstrap/app/src/test/resources/
        ├── user-management/
        │   ├── create-user-request.json
        │   ├── user-created-response.json
        │   └── get-user-response.json
        └── order-processing/
            ├── create-order-request.json
            └── order-created-response.json
        ```

* **Integration Test Guidelines:**
    * **Base Setup**: All integration tests inherit from `AppBaseSpecification` which is pre-configured with:
        * `@SpringBootTest` with `webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT` for full application context
        * `@Testcontainers` with PostgreSQL container for database integration tests
        * `@AutoConfigureMockMvc` for web layer testing with `MockMvc` available
        * Dynamic property configuration to connect to the test database container
    * **HTTP Testing**: Use the injected `MockMvc` from `AppBaseSpecification` for web endpoint testing, or alternatively use `TestRestTemplate` or `WebTestClient`
    * **Database Testing**: The PostgreSQL test container is automatically started and configured - no additional setup needed
    * **Test Data Cleanup**: Clean up test data between tests using `@Transactional` or explicit cleanup methods in your test setup/teardown
    * **Test Coverage**: Test both happy path and error scenarios, verifying HTTP status codes, response bodies, and database state changes

* **Test Naming Convention:**
    * Use descriptive test method names that clearly indicate what is being tested
    * Format: `should_[expected_behavior]_when_[condition]`
    * Examples:
        * `should_create_user_when_valid_request_provided()`
        * `should_return_404_when_user_not_found()`
        * `should_update_user_email_when_valid_email_provided()`
