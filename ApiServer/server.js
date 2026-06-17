import express from 'express';
import cors from 'cors';
import helmet from 'helmet';
import dotenv from 'dotenv';
import authRoutes from './src/routes/authRoutes.js';
import publicRoutes from './src/routes/publicRoutes.js';
import noteRoutes from './src/routes/noteRoutes.js';
import { errorHandler } from './src/middleware/errorHandler.js';
import { sendError } from './src/utils/response.js';

// Load environmental variables
dotenv.config();

const app = express();
const PORT = process.env.PORT || 5000;

// Security and utility middlewares
app.use(helmet());
app.use(cors());
app.use(express.json());

// API Routes
app.use('/user', authRoutes);
app.use('/', publicRoutes);
app.use('/', noteRoutes);

// Root route (sanity check)
app.get('/', (req, res) => {
  res.json({
    success: true,
    message: 'LMS REST API Server is running.',
    details: 'Use Postman or Rest Assured to interact with endpoints.'
  });
});

// 404 Fallback for unmatched routes
app.use((req, res, next) => {
  return sendError(res, `Route ${req.method} ${req.originalUrl} not found`, 404);
});

// Centralized Error Handling Middleware
app.use(errorHandler);

// Start Server
app.listen(PORT, () => {
  console.log(`=========================================`);
  console.log(` LMS REST API Server Started Successfully`);
  console.log(` Port: ${PORT}`);
  console.log(` Environment: ${process.env.NODE_ENV || 'development'}`);
  console.log(` Preloaded User: sam@gmail.com / 123`);
  console.log(`=========================================`);
});

export default app;
