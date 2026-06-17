import { notes } from '../data/db.js';
import { sendSuccess, sendError } from '../utils/response.js';

// 1. Create Note
export const createNote = async (req, res, next) => {
  try {
    const { title, content, tags = [], isPinned = false, color = 'white' } = req.body;

    if (!title || !content) {
      return sendError(res, 'Title and content are required', 400);
    }

    if (!Array.isArray(tags)) {
      return sendError(res, 'Tags must be an array of strings', 400);
    }

    const newNote = {
      id: `note-${Date.now()}-${Math.floor(Math.random() * 1000)}`,
      title,
      content,
      tags,
      isPinned: !!isPinned,
      color,
      userId: req.user.id,
      createdAt: new Date().toISOString(),
      lastEdited: new Date().toISOString()
    };

    notes.push(newNote);

    return sendSuccess(res, 'Note created successfully', newNote, 201);
  } catch (error) {
    next(error);
  }
};

// 2. Get All Notes
export const getAllNotes = async (req, res, next) => {
  try {
    const {
      page = 1,
      limit = 10,
      search,
      tags,
      isPinned,
      sortBy = 'createdAt',
      sortOrder = 'desc'
    } = req.query;

    // Filter by authenticated user's ID
    let userNotes = notes.filter((note) => note.userId === req.user.id);

    // Apply Search Filter (Case-insensitive match in title or content)
    if (search) {
      const searchLower = search.toLowerCase();
      userNotes = userNotes.filter(
        (note) =>
          note.title.toLowerCase().includes(searchLower) ||
          note.content.toLowerCase().includes(searchLower)
      );
    }

    // Apply Tags Filter (Case-insensitive check for tag match)
    if (tags) {
      const tagList = tags.split(',').map((t) => t.trim().toLowerCase());
      userNotes = userNotes.filter(
        (note) =>
          note.tags &&
          note.tags.some((tag) => tagList.includes(tag.toLowerCase()))
      );
    }

    // Apply Pinned Filter
    if (isPinned !== undefined) {
      const isPinnedBool = isPinned === 'true';
      userNotes = userNotes.filter((note) => note.isPinned === isPinnedBool);
    }

    // Apply Sorting
    const order = sortOrder.toLowerCase() === 'asc' ? 1 : -1;
    userNotes.sort((a, b) => {
      let valA = a[sortBy];
      let valB = b[sortBy];

      // fallback to empty string if undefined to avoid runtime crash
      if (valA === undefined) valA = '';
      if (valB === undefined) valB = '';

      if (typeof valA === 'string') {
        valA = valA.toLowerCase();
        valB = valB.toLowerCase();
      }

      if (valA < valB) return -1 * order;
      if (valA > valB) return 1 * order;
      return 0;
    });

    // Apply Pagination
    const totalCount = userNotes.length;
    const pageNum = parseInt(page, 10) || 1;
    const limitNum = parseInt(limit, 10) || 10;
    const totalPages = Math.ceil(totalCount / limitNum);
    const startIndex = (pageNum - 1) * limitNum;
    const endIndex = pageNum * limitNum;

    const paginatedNotes = userNotes.slice(startIndex, endIndex);

    return sendSuccess(res, 'Notes retrieved successfully', {
      notes: paginatedNotes,
      pagination: {
        totalCount,
        page: pageNum,
        limit: limitNum,
        totalPages
      }
    });
  } catch (error) {
    next(error);
  }
};

// 3. Get Note by ID
export const getNoteById = async (req, res, next) => {
  try {
    const { id } = req.params;

    const note = notes.find((n) => n.id === id);

    if (!note) {
      return sendError(res, 'Note not found', 404);
    }

    // Ensure user owns this note
    if (note.userId !== req.user.id) {
      return sendError(res, 'Forbidden: You do not have access to this note', 403);
    }

    return sendSuccess(res, 'Note retrieved successfully', note);
  } catch (error) {
    next(error);
  }
};

// 4. Update Note
export const updateNote = async (req, res, next) => {
  try {
    const { id } = req.params;
    const { title, content, tags, isPinned, color } = req.body;

    const note = notes.find((n) => n.id === id);

    if (!note) {
      return sendError(res, 'Note not found', 404);
    }

    // Ensure user owns this note
    if (note.userId !== req.user.id) {
      return sendError(res, 'Forbidden: You do not have access to this note', 403);
    }

    // Update only provided fields
    if (title !== undefined) note.title = title;
    if (content !== undefined) note.content = content;
    if (tags !== undefined) {
      if (!Array.isArray(tags)) {
        return sendError(res, 'Tags must be an array of strings', 400);
      }
      note.tags = tags;
    }
    if (isPinned !== undefined) note.isPinned = !!isPinned;
    if (color !== undefined) note.color = color;

    note.lastEdited = new Date().toISOString();

    return sendSuccess(res, 'Note updated successfully', note);
  } catch (error) {
    next(error);
  }
};

// 5. Toggle Pin
export const togglePin = async (req, res, next) => {
  try {
    const { id } = req.params;

    const note = notes.find((n) => n.id === id);

    if (!note) {
      return sendError(res, 'Note not found', 404);
    }

    // Ensure user owns this note
    if (note.userId !== req.user.id) {
      return sendError(res, 'Forbidden: You do not have access to this note', 403);
    }

    // Toggle isPinned
    note.isPinned = !note.isPinned;
    note.lastEdited = new Date().toISOString();

    return sendSuccess(res, `Note ${note.isPinned ? 'pinned' : 'unpinned'} successfully`, note);
  } catch (error) {
    next(error);
  }
};

// 6. Delete Note (Supports single and multiple slash-separated IDs)
export const deleteNote = async (req, res, next) => {
  try {
    const firstId = req.params.id;
    const extraIdsPath = req.params[0]; // Matches any extra path parts from the wildcard route

    const ids = [firstId];
    if (extraIdsPath) {
      const extraIds = extraIdsPath
        .split('/')
        .map((id) => id.trim())
        .filter((id) => id !== '');
      ids.push(...extraIds);
    }

    if (ids.length === 0) {
      return sendError(res, 'No note IDs provided for deletion', 400);
    }

    // Find notes to delete
    const notesToDelete = notes.filter((n) => ids.includes(n.id));

    if (notesToDelete.length === 0) {
      return sendError(res, 'No matching notes found', 404);
    }

    // Check ownership of all notes to delete
    const unauthorizedNotes = notesToDelete.filter((n) => n.userId !== req.user.id);
    if (unauthorizedNotes.length > 0) {
      return sendError(res, 'Forbidden: You do not have permission to delete one or more of these notes', 403);
    }

    // Delete in-place from the notes array
    notesToDelete.forEach((noteToDelete) => {
      const index = notes.findIndex((n) => n.id === noteToDelete.id);
      if (index !== -1) {
        notes.splice(index, 1);
      }
    });

    return sendSuccess(res, 'Notes deleted successfully', {
      deletedCount: notesToDelete.length,
      deletedIds: notesToDelete.map((n) => n.id)
    });
  } catch (error) {
    next(error);
  }
};
