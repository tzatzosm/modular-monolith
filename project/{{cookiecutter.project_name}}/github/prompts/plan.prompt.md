---
mode: agent
---

# Plan a New Feature

**Instruction to Copilot Agent:**

You are tasked with planning a new feature for this repository.

> **If the user does not provide a description for the new feature, prompt the user to supply one before proceeding.**

1. **Analyze the Codebase**

   - Explore the existing code relevant to this feature.
   - Identify related modules, classes, services, tests, and configurations.
   - Summarize your understanding of the current design and flow.

2. **Define the Feature Requirements**

   - Restate the feature request in your own words.
   - List assumptions and open questions (if any).

3. **Create a Feature Plan Document**

  - Use clear sections with headings.
  - Include the following structure:
    - **Feature Overview** – short description of the feature.
    - **Affected Components** – which parts of the codebase will change.
    - **Design Approach** – how the feature will integrate into the existing system.
    - **Data Model Changes** – entities, database tables, DTOs.
      - **For each entity provide describe all the fields in detail along with their data type.**
      - Example:
        - UserEntity:
          - Name: string
          - Age: int
          - Gender: Enum(Male, Female, Other)
      - **For each change, explicitly list all files that should be created or modified, with their full filepaths.**
      - Example:
        - Entity: `features/user-management/src/main/java/com/example/modularmonolith/usermanagement/domain/model/User.java`
        - DTO: `features/user-management/src/main/java/com/example/modularmonolith/usermanagement/api/model/UserDto.java`
        - Migration: `features/database/src/main/resources/changesets/2025_08_25-create-users-table.sql`
    - **API Changes** – new endpoints or modifications.
    - **Error Handling & Edge Cases** – potential pitfalls.
    - **Testing Strategy** – unit, integration, e2e tests.
    - **Migration/Backward Compatibility** (if applicable).
    - **Open Questions** – things to confirm before starting implementation.

4. **Implementation Steps**

   Create a detailed step-by-step implementation plan with the following structure:

   **Step 1: Integration Tests**

   - List all integration test files to be created or modified
   - For each test file, specify:
     - Full file path (e.g., `bootstrap/app/src/test/groovy/com/example/modularmonolith/app/UserManagementSpecification.groovy`)
     - Test scenarios to cover (happy path, error cases, edge cases)
     - Required test data files with their paths (e.g., `bootstrap/app/src/test/resources/user-management/create-user-request.json`)
   - Define the expected API behavior before implementation

   **Step 2: Database Migrations**

   - List all database migration files to be created
   - For each migration file, specify:
     - Full file path following naming convention (e.g., `features/database/src/main/resources/changesets/2025_08_25-create-users-table.sql`)
     - Tables to create/modify
     - Columns, indexes, constraints, and relationships
     - Required updates to `features/database/src/main/resources/changelog.xml`

   **Step 3: Implementation**

   - Break down into substeps by architectural layer:

     **Step 3.1: Domain Layer**

     - Domain entities and value objects
     - Domain service interfaces (ports)
     - Business rules and validation logic

     **Step 3.2: Application Layer**

     - Use case interfaces and implementations
     - Application services
     - Cross-cutting concerns (validation, transactions)

     **Step 3.3: Infrastructure Layer**

     - Repository implementations
     - External service adapters
     - Configuration classes

     **Step 3.4: API Layer**

     - REST controllers
     - Request/response DTOs
     - API documentation annotations

   For each substep, list:

   - Specific files to create or modify with full file paths
   - Key responsibilities and interfaces
   - Dependencies between components

5. **Output Format**
   - Write the plan as a new Markdown file in `.vibe/plans/<feature-name>.md`.
   - Ensure the document is self-contained and understandable to another developer.
   - The implementation steps should be detailed enough that each step can be executed independently.

**IMPORTANT:** While working on this prompt, do NOT add any implementation code. Only update and work on this plan file until the planning is complete.
