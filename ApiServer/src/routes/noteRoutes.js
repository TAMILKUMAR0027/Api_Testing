import express from 'express';
import { protect } from '../middleware/auth.js';
import {
  createNote,
  getAllNotes,
  getNoteById,
  updateNote,
  togglePin,
  deleteNote
} from '../controllers/noteController.js';

const router = express.Router();

// Apply JWT auth protection middleware to all note routes
router.use(protect);

router.post('/create/notes', createNote);
router.get('/getAll/notes', getAllNotes);
router.get('/getById/notes/:id', getNoteById);
router.put('/update/notes/:id', updateNote);
router.put('/toggle-pin/notes/:id', togglePin);

// Express route with optional wildcard suffix to capture slash-separated IDs (e.g. /ById/id1/id2/id3)
router.delete('/delete/notes/ById/:id*', deleteNote);

export default router;
