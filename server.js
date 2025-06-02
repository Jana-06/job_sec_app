require('dotenv').config({ path: './C:/Users/Janarthan S/StudioProjects/job_sec_app/backend/.env' });

const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const http = require('http');
const app = express();

// Middleware
app.use(cors());
app.use(express.json());

// MongoDB Connection
async function connectDB() {
  try {
    await mongoose.connect("mongodb://127.0.0.1:27017/task2");
    console.log('MongoDB Connected...');
  } catch (err) {
    console.error('Connection error:', err.message);
    process.exit(1);
  }
}

const internshipSchema = new mongoose.Schema({
  title: String,
  company: String,
  description: String,
  stipend: String
}, { strict: false });

const Internship = mongoose.model('Internship', internshipSchema, 'internship');

// Route: Get internships
app.get('/api/internships', async (req, res) => {
  try {
    const internships = await Internship.find();
    res.json(internships);  // Send proper JSON response
  } catch (err) {
    res.status(500).json({ message: err.message });
  }
});

// Route: Update internship
app.put('/api/internships/:id', async (req, res) => {
  try {
    const updatedInternship = await Internship.findByIdAndUpdate(
      req.params.id,
      req.body,
      { new: true }
    );
    res.json(updatedInternship);
  } catch (err) {
    res.status(400).json({ message: err.message });
  }
});

// Start server after DB connects
async function startServer() {
  await connectDB();

  http.createServer(app).listen(5000, () => {
    console.log("Server started on port 5000");
  });
}

startServer();
