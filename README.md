# Pet Management Application

Full-stack web application for managing pets.  
Built with **Spring Boot** (backend), **Vue 3 + Pinia** (frontend), and **PostgreSQL** (database).  
Supports user authentication with JWT tokens, CRUD operations on pets, validation, and Docker-based deployment.

---

## Features

- User registration and login with JWT authentication  
- Session-based login (token stored in `sessionStorage`, auto-logout on expiration)  
- Add, edit, soft-delete (mark inactive), and list pets  
- Validation with clear error messages:
  - Pet name → only letters, digits, spaces, underscores, parentheses  
  - Identification number → digits only, max 12 characters  
  - Fur color → letters only  
- Live check for unique identification number  
- Toast notifications and form field highlights for validation errors  
- Route guards: protected pages require login  
- Dockerized backend, frontend, and database with `docker compose`

---

## Tech Stack

- **Backend:** Java 21, Spring Boot 3, Spring Security (JWT), JPA (Hibernate), Liquibase  
- **Frontend:** Vue 3, Pinia, Vue Router, Axios, Bootstrap 5  
- **Database:** PostgreSQL (production), H2 (dev/test)  
- **Deployment:** Docker, Docker Compose, Nginx (for SPA)

---

## Getting Started

### Prerequisites
- Docker & Docker Compose installed

### Run with Docker
```bash
docker compose up --build
```

### The Services
- Backend: http://localhost:8080/api
- Frontend: http://localhost:8080

Default database runs on localhost:5436 (see docker-compose.yml).

### Development Notes
- Validation errors return structured JSON from backend (field → message).
- Inactive pets are hidden from the list, but remain in the database.
- JWT tokens expire automatically, and session is cleared in the frontend.
- Route guards redirect unauthorized users to /login.