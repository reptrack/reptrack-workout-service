# RepTrack

A comprehensive workout tracking application that helps users log, track, and manage their fitness routines.

## Project Structure

This repository contains both the frontend and backend components of the RepTrack application:

- `frontend/` - React-based web application
- `backend/` - Spring Boot REST API

## Frontend

The frontend is built with React, TypeScript, and Vite, providing a modern and responsive user interface.

### Features
- User authentication and authorization
- Exercise library browsing
- Workout logging and tracking
- Exercise set tracking
- Responsive design for mobile and desktop

### Tech Stack
- React 19
- TypeScript
- Vite
- ESLint

### Getting Started with Frontend

1. Navigate to the frontend directory:
```bash
cd frontend
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm run dev
```

4. Build for production:
```bash
npm run build
```

## Backend

The backend is a robust RESTful API built with Spring Boot, providing secure authentication and comprehensive workout tracking functionality.

### Features
- JWT-based authentication
- User management with role-based authorization
- Exercise library management
- Workout logging and tracking
- Exercise set tracking within workouts
- Exercise approval system

### Tech Stack
- Java 17
- Spring Boot 3.2.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- JWT Authentication
- Maven
- Docker

### Getting Started with Backend

1. Navigate to the backend directory:
```bash
cd backend
```

2. Set up environment variables:
```bash
# Create a .env file with:
JWT_SECRET=your_jwt_secret_here
```

3. Build the project:
```bash
./mvnw clean install
```

4. Run the application:
```bash
./mvnw spring-boot:run
```

5. Run tests:
```bash
./mvnw test
```

## API Documentation

The backend API provides the following endpoints:

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

## Docker Deployment

Both frontend and backend can be deployed using Docker:

1. Build and run the backend:
```bash
cd backend
docker-compose up --build
```

2. Build and run the frontend:
```bash
cd frontend
docker build -t reptrack-frontend .
docker run -p 3000:80 reptrack-frontend
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
