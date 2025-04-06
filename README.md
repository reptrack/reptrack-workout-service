# 🏋️ reptrack-backend

**RepTrack** is a RESTful Spring Boot API that powers a fitness tracking application. It allows users to log workouts, manage exercises, and track progress over time.

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
