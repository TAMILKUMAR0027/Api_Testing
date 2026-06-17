import { institutions, roles, courses } from '../data/db.js';
import { sendSuccess } from '../utils/response.js';

export const getInstitutions = (req, res, next) => {
  try {
    return sendSuccess(res, 'Institutions retrieved successfully', institutions);
  } catch (error) {
    next(error);
  }
};

export const getRoles = (req, res, next) => {
  try {
    return sendSuccess(res, 'Roles retrieved successfully', roles);
  } catch (error) {
    next(error);
  }
};

export const getCoursesStructure = (req, res, next) => {
  try {
    return sendSuccess(res, 'Courses structure retrieved successfully', courses);
  } catch (error) {
    next(error);
  }
};
