# job_sec_app
# 📱 Internship Listing App

A simple Android application built using **Kotlin**, **ViewModel**, **LiveData**, **Retrofit**, and **RecyclerView** to display internship listings from a **Node.js + Express + MongoDB** backend.

---

## 🌐 Backend (Node.js + Express + MongoDB)

### 📁 Directory: `/backend`

### 🔧 Features
- Connects to MongoDB using Mongoose
- Provides a REST API to:
  - List internships (`GET /api/internships`)
  - Add internships (`POST /api/internships`)
  - Update internships (`PUT /api/internships/:id`)

### 📦 Prerequisites
- Node.js
- MongoDB running locally (default port 27017)

### ▶️ Run the server
```bash
cd backend
npm install
node server.js

