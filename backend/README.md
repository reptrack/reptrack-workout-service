# RepTrack API

A robust RESTful API for workout tracking and exercise management built with Spring Boot.

## Features

- 🔐 Secure JWT-based authentication
- 👤 User management with role-based authorization
- 💪 Exercise library management
- 📝 Workout logging and tracking
- 🏋️‍♂️ Exercise set tracking within workouts
- ✅ Exercise approval system

## Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven
- Docker

## API Endpoints

### Authentication
```
POST /api/v1/auth/register     - Register new user
POST /api/v1/auth/authenticate - Login user
```

### Exercises
```
GET    /api/v1/exercises         - List exercises
POST   /api/v1/exercises         - Create exercise
DELETE /api/v1/exercises/{id}    - Delete exercise
PATCH  /api/v1/exercises/{id}    - Update exercise
PATCH  /api/v1/exercises/{id}/approve - Approve exercise
```

### Workout Logs
```
GET    /api/v1/workout-logs      - Get user's workout logs
GET    /api/v1/workout-logs/{id} - Get specific workout
POST   /api/v1/workout-logs      - Create workout log
PUT    /api/v1/workout-logs/{id} - Update workout log
DELETE /api/v1/workout-logs/{id} - Delete workout log
```

### Workout Exercises
```
POST   /api/v1/workout-exercises/log/{logId} - Add exercise to workout
PATCH  /api/v1/workout-exercises/{id}        - Update workout exercise
DELETE /api/v1/workout-exercises/{id}        - Remove exercise from workout
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven
- PostgreSQL
- Docker (optional)

### Environment Variables

Create a `.env` file in the root directory with:

```env
JWT_SECRET=your_jwt_secret_here
```

### Running Locally

1. Clone the repository
```bash
git clone https://github.com/yourusername/reptrack.git
cd reptrack/backend
```

2. Build the project
```bash
./mvnw clean install
```

3. Run the application
```bash
./mvnw spring-boot:run
```

### Running with Docker

1. Build and run using Docker Compose
```bash
docker-compose up --build
```

## Testing

Run the test suite:
```bash
./mvnw test
```
