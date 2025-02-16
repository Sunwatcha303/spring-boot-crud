# Spring Boot CRUD Application
![Coverage](https://codecov.io/gh/Sunwatcha303/spring-boot-crud/branch/main/graph/badge.svg)

This is a simple CRUD (Create, Read, Update, Delete) application built using Spring Boot.

## Features
- Create, Read, Update, and Delete operations on entities.
- RESTful API endpoints.
- Uses Spring Data JPA for database interactions.
- Integrated with a relational database (e.g., MySQL, PostgreSQL, or H2 for testing).
- Basic validation and error handling.

## Technologies Used
- Java
- Spring Boot
- Spring Data JPA
- Spring Web
- MySQL / PostgreSQL / H2
- Gradle

## Setup Instructions

### Prerequisites
- Java 17+ installed
- Gradle installed
- MySQL/PostgreSQL installed (if using a persistent database)

### Steps to Run
1. Clone the repository:
   ```sh
   git clone https://github.com/Sunwatcha303/spring-boot-crud.git
   cd spring-boot-crud
   ```

2. Configure database settings in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=root
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

3. Build the project:
   ```sh
   ./gradlew build
   ```

4. Run the application:
   ```sh
   ./gradlew bootRun
   ```

### API Endpoints
| Method | Endpoint       | Description |
|--------|--------------|-------------|
| GET    | `/user` | Get all users |
| GET    | `/user/{id}` | Get user by ID |
| POST   | `/user` | Create a new user |
| PUT    | `/user/{id}` | Update an existing user |
| DELETE | `/user/{id}` | Delete an user |

## Testing
You can test the API using tools like Postman or `curl`. For example:
```sh
curl -X GET http://localhost:8081/user
```

