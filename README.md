# 🏋️ reptrack-workout-service

This is the **Workout Service** for the RepTrack system — a Spring Boot microservice responsible for managing **exercises** and **workout logs**. It exposes RESTful endpoints to track a user’s fitness activities over time.

This service is part of the broader [RepTrack microservice architecture](https://github.com/reptrack).

---

## 📬 API Endpoints

| Method | Endpoint                               | Description                      |
|--------|----------------------------------------|----------------------------------|
| GET    | `/api/v1/exercises`                    | Get all exercises                |
| POST   | `/api/v1/exercises`                    | Add a new exercise               |
| PUT    | `/api/v1/exercises/{id}`               | Update exercise by ID            |
| DELETE | `/api/v1/exercises/{id}`               | Delete exercise by ID            |
| GET    | `/api/v1/workout-logs`                 | Get all workout logs             |
| POST   | `/api/v1/workout-logs`                 | Add a new workout log            |
| PUT    | `/api/v1/workout-logs/{workoutlogId}`  | Update workout log by ID         |
| DELETE | `/api/v1/workout-logs/{workoutlogId}`  | Delete workout log by ID         |

---

## 📄 License

MIT License — see [LICENSE](./LICENSE) for details.
