# Modular Monolith Cookiecutter Template

A comprehensive Cookiecutter template for creating Spring Boot modular monolith applications with hexagonal architecture, following Domain-Driven Design principles.

## 🚀 Features

- **Modular Architecture**: Clean separation between features and bootstrap layers
- **Hexagonal Architecture**: Domain-driven design with ports and adapters pattern
- **Spring Boot 3.5.3**: Latest Spring Boot with modern Java features
- **Gradle Build System**: Multi-module build with custom plugins
- **Testing Framework**: Spock framework for expressive testing
- **Development Tools**: Lombok for boilerplate reduction
- **Containerization**: JIB plugin for Docker image creation
- **Code Quality**: Comprehensive test setup and project structure

## 📋 Prerequisites

- **Java 21** or higher
- **Python 3.7+** with pip
- **Cookiecutter** (`pip install cookiecutter`)
- **Git** for version control

## 🛠️ Installation

### 1. Install Cookiecutter

```bash
pip install cookiecutter
```

### 2. Generate Project

```bash
cookiecutter https://github.com/tzatzosm/modular-monolith --directory=project
```

Or if you have the template locally:

```bash
cookiecutter path/to/modular-monolith --directory=project
```

### 3. Configuration Options

During project generation, you'll be prompted for:

| Parameter | Description | Default Value | Example |
|-----------|-------------|---------------|---------|
| `project_name` | Project name | `ModularMonolith` | `MyAwesomeApp` |
| `package` | Base Java package | `com.example.modularmonolith` | `com.mycompany.myapp` |
| `description` | Project description | `A modular monolith application...` | `My business application` |
| `author` | Author name | `Your Name` | `John Doe` |
| `email` | Author email | `your.email@example.com` | `john.doe@company.com` |
| `version` | Initial version | `0.1.0` | `1.0.0` |
| `version_control` | VCS choice | `git` | `git` or `none` |
| `spring_boot_version` | Spring Boot version | `3.5.3` | `3.5.3` |
| `lombok_version` | Lombok version | `1.18.38` | `1.18.38` |
| `spock_version` | Spock version | `2.4-M4-groovy-4.0` | `2.4-M4-groovy-4.0` |

## 📁 Project Structure

```
my-project/
├── build.gradle                        # Root build configuration
├── settings.gradle                     # Multi-module configuration
├── gradle/
│   └── libs.versions.toml              # Version catalog
├── buildSrc/                           # Custom Gradle plugins
│   ├── build.gradle
│   └── src/main/groovy/
│       ├── buildlogic.java-application.gradle
│       ├── buildlogic.java-core.gradle
│       └── buildlogic.java-library.gradle
├── bootstrap/                          # Application bootstrap layer
│   └── app/
│       ├── build.gradle
│       ├── src/main/java/
│       │   └── com/example/app/
│       │       └── MyProjectApplication.java
│       └── src/test/groovy/
│           └── com/example/app/
│               ├── AppBaseSpecification.groovy
│               └── hello/
│                   └── HelloSpecification.groovy
└── features/                           # Feature modules
    └── hello/
        ├── build.gradle
        └── src/main/java/
            └── com/example/hello/
                ├── api/                 # REST controllers
                │   └── HelloController.java
                ├── application/         # Use cases
                │   ├── GetHelloMessageUseCase.java
                │   └── GetHelloMessageByNameUseCase.java
                ├── domain/              # Domain layer
                │   ├── model/
                │   │   ├── Message.java
                │   │   └── Name.java
                │   └── ports/
                │       └── HelloMessageService.java
                └── infrastructure/      # External adapters
                    └── HelloMessageServiceImpl.java
```

## 🏗️ Architecture Overview

### Modular Monolith Design

The template follows a modular monolith architecture with clear boundaries:

- **Bootstrap Layer**: Contains the main application class and configuration
- **Feature Modules**: Independent modules for each business capability

### Hexagonal Architecture

Each feature module follows hexagonal architecture principles:

```
Feature Module
├── api/           # Primary adapters (REST controllers)
├── application/   # Use cases and application services
├── domain/        # Domain entities, value objects, and ports
└── infrastructure/ # Secondary adapters (implementations)
```

## 🚀 Getting Started

### 1. Build the Project

```bash
cd my-project
./gradlew build
```

### 2. Run the Application

```bash
./gradlew bootRun
```

### 3. Test the Application

```bash
# Run all tests
./gradlew test
```

### 4. Access the Application

- **Base URL**: `http://localhost:8080`
- **Hello Endpoint**: `http://localhost:8080/hello`
- **Actuator**: `http://localhost:8080/actuator`

### 5. Run Database Migrations

Database migrations are managed using the [Liquibase Gradle Plugin](https://github.com/liquibase/liquibase-gradle-plugin).

To apply migrations, you have two options:

- **From the project root:**
  ```bash
  ./gradlew :features:database:update
  ```
- **From the `features/database` module directory:**
  ```bash
  ./gradlew update
  ```

You can customize the database connection by setting the following environment variables:
- `DB_URL` – JDBC URL for the database
- `MIGRATIONS_DB_USER` – database username
- `MIGRATIONS_DB_PASS` – database password

## 📊 Available Gradle Tasks

| Task | Description |
|------|-------------|
| `./gradlew build` | Build the entire project |
| `./gradlew test` | Run all tests |
| `./gradlew bootRun` | Run the Spring Boot application |
| `./gradlew jibDockerBuild` | Build Docker image |
| `./gradlew clean` | Clean build artifacts |
| `./gradlew check` | Run all checks (tests, linting) |

## 🧪 Testing

### Test Structure

- **Unit Tests**: Test individual components in isolation
- **Integration Tests**: Test feature modules end-to-end
- **Spock Framework**: Expressive BDD-style tests

### Example Test

```groovy
class HelloSpecification extends AppBaseSpecification {
  
  @Autowired private MockMvc mvc

  def 'hello endpoint returns greeting message'() {
    when:
    def responseBody = mvc.perform(get('/hello'))
        .andExpect(status().isOk())
        .andReturn()
        .response
        .contentAsString

    then:
    responseBody == '{"value":"Hello, World!"}'
  }
}
```

## 🐳 Docker Support

### Build Docker Image

```bash
./gradlew jibDockerBuild
```

### Run with Docker

```bash
docker run -p 8080:8080 my-project:latest
```

## 🏛️ Adding New Features

### 1. Create Feature Module

```bash
mkdir -p features/user
cd features/user
```

### 2. Add build.gradle

```gradle
plugins {
    alias(libs.plugins.library)
}

dependencies {
    implementation libs.spring.boot.starter
    implementation libs.spring.boot.starter.web
    implementation libs.spring.boot.starter.validation
}
```

### 3. Update settings.gradle

```gradle
include 'features:user'
```

### 4. Create Package Structure

```
features/user/src/main/java/com/example/user/
├── api/
├── application/
├── domain/
│   ├── model/
│   └── ports/
└── infrastructure/
```

### 5. Update Bootstrap Dependencies

Add to `bootstrap/app/build.gradle`:

```gradle
dependencies {
    implementation project(':features:user')
    // ... other dependencies
}
```

## 📚 Best Practices

### Domain Modeling

- Use **Value Objects** for primitive obsession
- Implement **Aggregates** for consistency boundaries
- Define **Domain Services** for business logic

### Testing Strategy

- **Unit Tests**: Test domain logic in isolation
- **Integration Tests**: Test API endpoints
- **Contract Tests**: Test between modules

### Code Organization

- Keep **domain** layer pure (no external dependencies)
- Use **ports** for external integrations
- Implement **adapters** for specific technologies

## 🔧 Customization

### Adding Dependencies

Update `gradle/libs.versions.toml`:

```toml
[versions]
jackson = "2.15.2"

[libraries]
jackson-databind = { group = "com.fasterxml.jackson.core", name = "jackson-databind", version.ref = "jackson" }
```

### Custom Gradle Plugins

Extend `buildSrc/src/main/groovy/buildlogic.java-core.gradle`:

```gradle
dependencies {
    implementation libs.jackson.databind
    // Add common dependencies
}
```

## 📖 Documentation

### API Documentation

The template supports OpenAPI documentation:

```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User Management")
public class UserController {
    // Controller implementation
}
```

### Code Documentation

- Use **JavaDoc** for public APIs
- Document **domain concepts** thoroughly
- Maintain **architecture decisions** in ADR format

## 🤝 Contributing

### Development Setup

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `./gradlew test`
5. Submit a pull request

### Code Style

- Follow **Google Java Style Guide**
- Use **Lombok** for boilerplate reduction
- Write **expressive tests** with Spock

## 📄 License

This template is released under the MIT License. See [LICENSE](LICENSE) for details.

## 🆘 Support

### Common Issues

**Build fails with Java version error**
```bash
# Check Java version
java -version
# Should be Java 21 or higher
```

**Tests fail with package not found**
```bash
# Clean and rebuild
./gradlew clean build
```

### Getting Help

- **GitHub Issues**: Report bugs and request features at https://github.com/tzatzosm/modular-monolith/issues
- **Discussions**: Ask questions and share ideas
- **Documentation**: Check the wiki for detailed guides

## 🚦 Roadmap

- [ ] Add support for database integration
- [ ] Include security configuration
- [ ] Add monitoring and observability
- [ ] Support for event-driven architecture
- [ ] CI/CD pipeline templates

---

**Happy coding! 🎉**

For more information, visit the [project documentation](https://github.com/tzatzosm/modular-monolith/wiki).
