# 🏋️ RepTrack

RepTrack is a full-stack workout tracking platform that allows users to log workouts, track progress, and view exercise-based leaderboards. The backend is powered by Spring Boot and PostgreSQL, while the frontend (in progress) uses React and TypeScript.

---

## 📁 Project Structure

```
reptrack-main/
├── backend/     # Spring Boot REST API with Swagger Docs
└── frontend/    # React + TypeScript SPA (TODO)
```

---

## ⚙️ Backend – Spring Boot API

### ✨ Features

- Java 17 + Spring Boot 3.2  
- RESTful API for workout and exercise management  
- PostgreSQL database  
- Redis caching for leaderboard performance  
- Docker Compose support  
- Swagger UI for live API documentation

---

### 🚀 Getting Started

#### Prerequisites

- Java 17  
- Maven  
- Docker

#### Run with Maven

```bash
cd backend
./mvnw spring-boot:run
```

#### Run with Docker

```bash
cd backend
docker-compose up --build
```

---

### 🧪 Running Tests

```bash
cd backend
./mvnw test
```

---

## 📘 Swagger API Documentation

Once running, access live API docs at:

```
http://localhost:8080/swagger-ui/index.html
```

## 📌 Full API Endpoints

### Authentication
```
POST   /api/v1/auth/register           - Register new user
POST   /api/v1/auth/authenticate       - Login and receive JWT
```

### Exercises
```
GET    /api/v1/exercises               - List exercises
POST   /api/v1/exercises               - Create exercise
PATCH  /api/v1/exercises/{id}          - Update exercise
DELETE /api/v1/exercises/{id}          - Delete exercise
PATCH  /api/v1/exercises/{id}/approve  - Approve an exercise
```

### Workout Logs
```
GET    /api/v1/workout-logs            - List all user workout logs
GET    /api/v1/workout-logs/{id}       - Retrieve a single log
POST   /api/v1/workout-logs            - Create a new log
PUT    /api/v1/workout-logs/{id}       - Update a workout log
DELETE /api/v1/workout-logs/{id}       - Delete a workout log
```

### Workout Exercises
```
POST   /api/v1/workout-exercises/log/{logId} - Add exercise to workout log
PATCH  /api/v1/workout-exercises/{id}        - Update workout exercise
DELETE /api/v1/workout-exercises/{id}        - Delete exercise from log
```

### Leaderboard
```
GET    /api/v1/leaderboard/ping-redis        - Check Redis health
GET    /api/v1/leaderboard/{exercise}/{metric} - Get leaderboard by metric (e.g., weight, sets, reps)
```

---

## 🐳 Docker Deployment

To run everything with Docker:

### Backend

```bash
cd backend
docker-compose up --build
```

### Frontend

```bash
TODO
```

---

## 🎨 Frontend – React + TypeScript

### Features (TODO)

- JWT-based authentication
- Workout logging interface
- Leaderboard views per exercise
- Responsive design for all devices

### Tech Stack

- React
- TypeScript
- Vite
- Tailwind CSS

### Getting Started

```bash
cd frontend
npm install
npm run dev
```

---

## 📦 Tech Summary

- **Backend:** Java 17, Spring Boot 3, PostgreSQL, Redis, Swagger  
- **Frontend:** React, TypeScript, Vite
- **CI/CD & DevOps:** Docker, GitHub Actions

---

## 📄 License

This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for details.
