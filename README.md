# job_sec_app
📱 Internship Listing App
A simple Android application built with Kotlin, ViewModel, LiveData, Retrofit, and RecyclerView to display and apply for internship listings. The backend is powered by Node.js, Express, and MongoDB, with integrated support for Firebase and Google Drive API for file uploads.

🌟 Features
🔧 Android App (Kotlin)
List internships using RecyclerView (GET /api/internships)

Apply to internships with form input

Upload resume PDF to Firebase Storage

Retrieve Google Drive sharable link (WIP or optional)

MVVM architecture with ViewModel + LiveData

Retrofit for HTTP requests

Navigation between fragments (e.g., ApplyFragment)

🌐 Backend (Node.js + Express + MongoDB)
📁 Directory: /backend

Connects to MongoDB via Mongoose

Provides RESTful API endpoints:

GET /api/internships — fetch internship listings

POST /api/internships — add new internships

PUT /api/internships/:id — update existing internships

POST /api/apply — receive internship applications

Handles file uploads with Firebase Admin SDK and multer

📦 Prerequisites
Android App:
Android Studio

Firebase project with:

Storage bucket enabled

google-services.json in /app directory

Google Drive API (if using sharable link option)

Backend:
Node.js v18+

MongoDB (running locally on mongodb://localhost:27017)

Firebase Admin SDK credentials (firebase-adminsdk.json)

▶️ Run Backend
bash
Copy
Edit
cd backend
npm install
node server.js

📂 Application Data Flow
User selects an internship → clicks Apply

User fills the form and selects a PDF resume

Resume is uploaded to Firebase Storage

Application data + sharable resume URL is sent to backend via Retrofit POST

🔐 Security
Sensitive credentials like firebase-adminsdk.json are NOT committed to GitHub.

Resume files are uploaded securely to Firebase with unique naming.

🔧 TODO
 Add user authentication (Firebase Auth or JWT)

 Admin dashboard to view applicants

 Integrate push notifications

 Auto-delete old resumes from Firebase

📁 Tech Stack
Layer	Tools Used
UI	Kotlin, XML, RecyclerView, ViewModel
Networking	Retrofit, LiveData, ViewModel
Backend API	Node.js, Express.js
Database	MongoDB + Mongoose
File Upload	Firebase Storage, Google Drive API (WIP)



