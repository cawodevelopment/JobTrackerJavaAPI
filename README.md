# Job Tracker API

A Spring Boot REST API for managing job applications. The API uses PostgreSQL for persistence, Flyway for database migrations, and stateless JWT authentication with per-user data ownership.

## Technology

- Java 17
- Spring Boot 4.1.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- PostgreSQL
- Flyway
- JJWT
- Maven

## Getting started

### Prerequisites

- Java 17 or later
- PostgreSQL
- A database created for the application

### Configuration

The application reads the following environment variables:

| Variable | Required | Description |
| --- | --- | --- |
| `POSTGRES_URI` | Yes | PostgreSQL connection URI, without the `jdbc:` prefix |
| `POSTGRES_USERNAME` | Yes | PostgreSQL username |
| `POSTGRES_PASSWORD` | Yes | PostgreSQL password |
| `JWT_SECRET` | Recommended | Base64-encoded signing key |
| `JWT_EXPIRATION_MS` | No | Token lifetime in milliseconds; defaults to `86400000` (24 hours) |

Example:

```bash
export POSTGRES_URI="postgresql://localhost:5432/jobtracker"
export POSTGRES_USERNAME="postgres"
export POSTGRES_PASSWORD="your-password"
export JWT_SECRET="your-base64-encoded-secret"
export JWT_EXPIRATION_MS="86400000"
```

`JWT_SECRET` must decode to a key of at least 256 bits for the HMAC signing algorithm.

For local development, the application has a development fallback JWT secret. Set `JWT_SECRET` explicitly outside local development.

### Run the application

```bash
./mvnw spring-boot:run
```

The application starts on the default Spring Boot port:

```text
http://localhost:8080
```

Flyway runs the migrations in `src/main/resources/db/migration` on startup.

## Authentication

Authentication endpoints are public:

- `POST /auth/register`
- `POST /auth/login`

All other endpoints require a valid bearer token.

### Register

```bash
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

Passwords must be at least eight characters long.

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

The response body is the JWT as a plain string. Store it and send it with protected requests:

```bash
curl http://localhost:8080/job-applications \
  -H "Authorization: Bearer <token>"
```

The token contains the user's email as its subject and the user's database ID as the `userId` claim. The API validates both values before creating an authenticated request.

## Job application endpoints

All endpoints in this section require:

```http
Authorization: Bearer <token>
```

| Method | Endpoint | Description |
| --- | --- | --- |
| `GET` | `/job-applications` | Get the authenticated user's applications |
| `GET` | `/job-applications/{id}` | Get one of the authenticated user's applications |
| `POST` | `/job-applications` | Create an application for the authenticated user |
| `PUT` | `/job-applications/{id}` | Update one of the authenticated user's applications |
| `DELETE` | `/job-applications/{id}` | Delete one of the authenticated user's applications |

The list endpoint supports Spring Data pagination parameters such as `page`, `size`, and `sort`. The default page size is 10.

### Create an application

```bash
curl -X POST http://localhost:8080/job-applications \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "jobUrl": "https://example.com/jobs/123",
    "jobType": "FULL_TIME",
    "location": "London",
    "salaryMin": 40000,
    "salaryMax": 50000,
    "dateApplied": "2026-10-15",
    "deadline": "2026-10-31",
    "status": "APPLIED",
    "notes": "Follow up after one week"
  }'
```

Supported `jobType` values:

```text
INTERNSHIP, PLACEMENT, GRADUATE, FULL_TIME, PART_TIME
```

Supported `status` values:

```text
SAVED, APPLIED, ASSESSMENT, INTERVIEW, OFFER, REJECTED, WITHDRAWN
```

## Data ownership

Each job application has a required JPA `ManyToOne` relationship to a user, stored as the `user_id` foreign key.

Ownership is enforced at repository level:

- Application lists filter by the authenticated user's ID.
- Individual reads filter by both application ID and user ID.
- Updates only load records belonging to the authenticated user.
- Deletes only target records belonging to the authenticated user.
- New records are assigned to the authenticated user and do not accept a user ID from the request body.

This prevents a user from reading or modifying another user's job applications by changing an application ID in the URL.

## Database migrations

Flyway migrations are located in:

```text
src/main/resources/db/migration
```

Current migrations:

1. `V1__create_user_table.sql` creates the users table.
2. `V2__create_job_application_table.sql` creates the job applications table.
3. `V3__add_user_id_to_job_application.sql` adds the user foreign key.

`V3` leaves `user_id` nullable to allow databases with existing applications to migrate without inventing an owner for those rows. Existing rows without an owner will not be returned by ownership-scoped queries. New applications are assigned an owner by the application service.

Do not edit a Flyway migration that has already been applied to a shared or production database. Create a new migration for subsequent schema changes.

## Testing and packaging

Compile without running tests:

```bash
./mvnw -q -DskipTests compile
```

Build the application package:

```bash
./mvnw -q -DskipTests package
```

Run the test suite:

```bash
./mvnw test
```

The context test starts the full application, so PostgreSQL environment variables and an accessible database are required when running tests.

