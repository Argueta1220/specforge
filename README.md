# PC Compatibility Checker

A beginner-friendly full-stack project for checking basic PC part compatibility.

## Project Structure

```text
backend/   Java Spring Boot REST API
frontend/  React selection page
docs/      Notes for API usage
```

## Backend

The backend includes:

- Entities for CPU, Motherboard, RAM, and PSU
- Seed data in an in-memory catalog
- REST endpoints for listing parts and checking compatibility
- Unit tests for the compatibility rules

Run it from `backend/`:

```bash
mvn spring-boot:run
```

Run backend tests:

```bash
mvn test
```

## Frontend

The frontend includes:

- Dropdowns for CPU, Motherboard, RAM, and PSU
- A results section for compatible status, errors, warnings, and estimated wattage
- A Vite dev proxy from `/api` to `http://localhost:8080`

Run it from `frontend/`:

```bash
npm install
npm run dev
```

Then open `http://localhost:5173`.

## Compatibility Rules

1. CPU socket must match motherboard socket.
2. RAM type must match motherboard RAM type.
3. Estimated wattage must not exceed 80% of PSU wattage.
