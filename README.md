# Spring Boot To-Do List

Web application for managing a personal to-do list. Users can register, sign in, add tasks, mark them as completed, delete them, and filter tasks by status. The project also includes administrator roles for user management.

## Features

- registration and login by email;
- password hashing with BCrypt;
- personal account page with a user-specific task list;
- task creation, completion, deletion, and filtering;
- user roles: `USER`, `ADMIN`, `SUPER_ADMIN`;
- admin panel for deleting users;
- user promotion to administrator for `SUPER_ADMIN`;
- server-rendered HTML pages with Thymeleaf;
- custom pages for common errors.

## Tech Stack

- Java 17
- Spring Boot 2.6.15
- Spring MVC
- Spring Security
- Spring Data JPA / Hibernate
- Thymeleaf
- PostgreSQL
- Maven

## Requirements

Before running the application, make sure you have:

- JDK 17 or newer;
- PostgreSQL;
- Maven, or the included Maven Wrapper: `mvnw` / `mvnw.cmd`.
- Docker, optional, if you want to run PostgreSQL in a container.

## Database Setup

By default, the application connects to PostgreSQL using these settings:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/senseipostgres
spring.datasource.username=assanali
spring.datasource.password=12345678
```

Create the database:

```sql
CREATE DATABASE senseipostgres;
```

If your database name, username, or password is different, update:

```text
src/main/resources/application.properties
```

Hibernate is configured with:

```properties
spring.jpa.hibernate.ddl-auto=update
```

This means the database schema will be created or updated automatically when the application starts.

## Running PostgreSQL with Docker

The project does not include a `Dockerfile` or `docker-compose.yml` for the Spring Boot application. However, you can run the required PostgreSQL database with Docker:

```bash
docker run --name senseipostgres \
  -e POSTGRES_DB=senseipostgres \
  -e POSTGRES_USER=assanali \
  -e POSTGRES_PASSWORD=12345678 \
  -p 5432:5432 \
  -d postgres:14
```

On Windows PowerShell:

```powershell
docker run --name senseipostgres `
  -e POSTGRES_DB=senseipostgres `
  -e POSTGRES_USER=assanali `
  -e POSTGRES_PASSWORD=12345678 `
  -p 5432:5432 `
  -d postgres:14
```

If the container already exists, start it with:

```bash
docker start senseipostgres
```

## Running the Application

On Windows:

```bash
./mvnw.cmd spring-boot:run
```

On Linux/macOS:

```bash
./mvnw spring-boot:run
```

After startup, the application will be available at:

```text
http://localhost:8080
```

## Build

Build the project:

```bash
./mvnw.cmd clean package
```

Run the generated JAR:

```bash
java -jar target/to-do-list-application-0.0.1.jar
```

## Tests

Run tests:

```bash
./mvnw.cmd test
```

The project currently includes a basic Spring context loading test.

## Main Pages

| URL | Access | Description |
| --- | --- | --- |
| `/` | public | home page |
| `/registration` | public | registration page |
| `/login` | public | login page |
| `/account` | `USER`, `ADMIN`, `SUPER_ADMIN` | personal account and tasks |
| `/account?filter=active` | `USER`, `ADMIN`, `SUPER_ADMIN` | active tasks |
| `/account?filter=done` | `USER`, `ADMIN`, `SUPER_ADMIN` | completed tasks |
| `/admin` | `ADMIN`, `SUPER_ADMIN` | user management |
| `/super-admin/make-user-admin` | `SUPER_ADMIN` | promote a user to administrator |

## Project Structure

```text
src/main/java/com/sensei
├── config          # Spring Security configuration
├── controller      # MVC controllers
├── entity          # JPA entities and enums
├── repository      # Spring Data JPA repositories
└── service         # Business logic

src/main/resources
├── static/css      # Page styles
├── templates       # Thymeleaf templates
└── application.properties
```

## Roles

`USER` can manage their own task list.

`ADMIN` can access the admin panel and delete regular users.

`SUPER_ADMIN` can delete regular users and administrators, and can promote regular users to the `ADMIN` role.

## Development Notes

- Authentication uses email instead of username.
- New users receive the `USER` role after registration.
- New tasks are created with the `ACTIVE` status.
- Completed tasks receive the `DONE` status.
- CSRF protection is disabled in the current security configuration.

## Useful Commands

```bash
# Run the application
./mvnw.cmd spring-boot:run

# Run tests
./mvnw.cmd test

# Build the project
./mvnw.cmd clean package
```
