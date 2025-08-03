# Spring Boot Application

This is a simple Spring Boot application built with version **3.5.4**, using **Maven** as the build tool and **Spring Data JPA** with **Hibernate** for persistence.

## 📦 Technologies Used

- Java 17+
- Spring Boot 3.5.4
- Spring Data JPA
- Hibernate
- Maven
- PostgreSQL
- Lombok
- Swagger
- Spring Web (REST API)

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/duyle2002/learn-spring-boot
cd learn-spring-boot
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

### 4. Access the application
Swagger: http://localhost:8080/swagger-ui/index.html

### 5. Project structure
The project follows a layered architecture using packages for separation of concerns:

```bash
src/
└── main/
    ├── java/
    │   └── com/example/learnspringboot/
    │       ├── controller/       # Handles HTTP requests and responses (REST endpoints)
    │       ├── dto/              # Data Transfer Objects for input/output models
    │       ├── model/            # JPA entities that represent database tables
    │       ├── repository/       # Interfaces for database operations (DAO layer)
    │       ├── service/          # Business logic and application services
    │       ├── utils/            # Helper and utility classes (e.g. converters, formatters)
    │       └── LearnSpringBootApplication.java  # Main entry point of the Spring Boot application
    └── resources/
        ├── application.properties       # Application configuration (DB, ports, etc.)
        ├── static/               # Static web assets (CSS, JS, images, etc.)
        └── templates/            # Template files (Thymeleaf, FreeMarker, etc. if used)
```
