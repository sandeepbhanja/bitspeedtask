# bitespeed

Lightweight Spring Boot service that identifies contacts and groups primary/secondary records.

Live demo: https://bitspeedtask-production.up.railway.app

Backend-only: No frontend included — use Postman, curl or similar tools to test the API.

Quick start

Build:

```bash
./mvnw clean package
```

Run (needs Postgres):

```bash
JDBC=jdbc:postgresql://localhost:5432/db PGUSER=user PGPASSWORD=pass ./mvnw spring-boot:run
```

API

GET /identify (expects JSON body):

```json
{"phoneNumber":"+123...","emailId":"user@example.com"}
```

Example:

```bash
curl -X GET http://localhost:8080/identify -H 'Content-Type: application/json' -d '{"phoneNumber":"+123","emailId":"a@b.com"}'
```

Notes

- Java 21, Spring Boot 3.5.6
- Uses Postgres and native SQL in `BitespeedRepository`
- Controller currently uses GET with a request body; consider changing to POST for REST compliance
