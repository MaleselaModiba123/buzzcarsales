# Buzz Car Sales System

A Spring Boot REST API for managing a multi-branch car dealership franchise across South Africa.

---

## Features

- Full CRUD with pagination for all entities
- Multi-branch inventory management
- Car make, model and stock tracking
- Customer and employee management
- Sale transaction processing
- Automatic SMS confirmation on sale via SMSPortal
- PDF report generation (Sales, Available Cars, Employee Performance)

---

## Requirements

- Java 21+
- Maven 3.9+
- MySQL 8 (a `buzz_car_sales` database)

## Configuration

Sensitive settings are read from environment variables — **never commit real credentials**.
The defaults in `application.properties` are for local development only.

| Variable | Purpose | Default |
|----------|---------|---------|
| `DB_URL` | JDBC URL | `jdbc:mysql://localhost:3306/buzz_car_sales` |
| `DB_USERNAME` | Database user | `root` |
| `DB_PASSWORD` | Database password | `password` |
| `SMSPORTAL_API_KEY` | SMSPortal API key | _(empty)_ |
| `SMSPORTAL_API_SECRET` | SMSPortal API secret | _(empty)_ |
| `SMSPORTAL_API_URL` | SMSPortal endpoint | `https://rest.smsportal.com/BulkMessages` |


## Build & Run

```bash
mvn test          # run the test suite (uses in-memory H2)
mvn spring-boot:run   # start the API on http://localhost:8080
```

## API Documentation

Once running, interactive API docs (Swagger UI) are available at:

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI spec (JSON):** http://localhost:8080/v3/api-docs

---

## Project ERD

![alt text](<Screenshot (85).png>)
