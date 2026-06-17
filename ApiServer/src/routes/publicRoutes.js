import express from 'express';
import { protect } from '../middleware/auth.js';
import { getInstitutions, getRoles, getCoursesStructure } from '../controllers/publicController.js';

const router = express.Router();

// Public route
router.get('/getAll/institution', getInstitutions);

// Protected routes
router.get('/roles/getAll', protect, getRoles);
router.get('/courses-structure/getAll', protect, getCoursesStructure);

export default router;
