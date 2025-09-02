# Job Service Assessment

This project is a **Spring Boot 3 / Java 21** application that provides a REST API for searching and filtering job salary survey data.  
It demonstrates use of **Spring Data JPA, Specifications, MapStruct, Flyway, Lombok, and JUnit5/Mockito** with a clean architecture and best practices.

---

## Features

- Import salary survey data from a CSV file using **Flyway migration**.
- Persist jobs into a relational database (`job_data` table).
- REST API endpoint `/api/v1/job` that supports:
    - Filtering by `job_title`, `gender`, `salary` (gte, lte, gt, lt).
    - Sorting and pagination.
    - Sparse field projection (`?fields=job_title,gender,salary`).
- Separation of concerns with **DTOs, mappers, and services**.
- Unit tests for service, repository, specifications, and mappers.

---

## Tech Stack

- **Java 21**
- **Spring Boot 3.5.x**
- **Spring Data JPA** with **H2** (in-memory DB for dev/test)
- **Flyway** for DB migrations + CSV import
- **MapStruct** for DTO/entity mapping
- **Lombok** for boilerplate reduction
- **JUnit5 + Mockito** for testing

---

## Getting Started

### Prerequisites
- Java 21+
- Maven 3.9+

### Build & Run
```bash
# Clean and build
mvn clean install

# Run the app
mvn spring-boot:run
```

## Service

By default, the app runs on **http://localhost:8080**.

### Database
- **H2 in-memory DB** (auto-created on startup).
- **Flyway** runs migrations located in `src/main/resources/db/migration/`.
- Initial job data is loaded from `src/main/resources/data/job_data_clean.csv`.

### Run with Docker
- Run with Docker Compose
```bash
   docker compose up --build -d
```

#### This will:
- Start the job-service container
- Mount a volume jobdata:/data for the embedded H2 database files
- Expose port 8080

API is available at: http://localhost:8080/api/v1/job

### API Endpoint
#### GET /api/v1/job

#### Query Parameters

| Parameter       | Type        | Example                  | Description |
|-----------------|-------------|--------------------------|-------------|
| job_title       | String      | `engineer`              | Filter jobs by title (contains, case-insensitive). |
| gender          | String      | `F`                     | Filter by gender. |
| salary_gte      | BigDecimal  | `120000`                | Salary greater than or equal to. |
| salary_lte      | BigDecimal  | `150000`                | Salary less than or equal to. |
| salary_gt       | BigDecimal  | `100000`                | Salary strictly greater than. |
| salary_lt       | BigDecimal  | `200000`                | Salary strictly less than. |
| fields          | CSV string  | `job_title,salary`      | Sparse field projection. |
| sort            | String      | `salary`                | Sort field (`salary`, `gender`, `employer`, `location`, default = `jobTitle`). |
| sort_type       | String      | `DESC`                  | Sort order (`ASC`/`DESC`). |
| page            | Integer     | `0`                     | Page number (default 0). |
| size            | Integer     | `20`                    | Page size (default 50). |

---
