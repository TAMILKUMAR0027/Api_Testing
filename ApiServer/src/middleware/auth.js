import jwt from 'jsonwebtoken';
import { users } from '../data/db.js';
import { sendError } from '../utils/response.js';

export const protect = (req, res, next) => {
  let token;

  if (
    req.headers.authorization &&
    req.headers.authorization.startsWith('Bearer')
  ) {
    token = req.headers.authorization.split(' ')[1];
  }

  if (!token) {
    return sendError(res, 'Not authorized, no token provided', 401);
  }

  try {
    const decoded = jwt.verify(token, process.env.JWT_SECRET || 'your_super_secret_jwt_key_here');

    // Find user in memory database
    const user = users.find((u) => u.id === decoded.id || u.email === decoded.email);
    if (!user) {
      return sendError(res, 'Not authorized, user no longer exists', 401);
    }

    // Attach user context to request
    req.user = {
      id: user.id,
      email: user.email,
      role: user.role,
      name: user.name
    };

    next();
  } catch (error) {
    console.error('JWT verification failed:', error.message);
    if (error.name === 'TokenExpiredError') {
      return sendError(res, 'Token has expired', 401);
    }
    return sendError(res, 'Not authorized, invalid token', 401);
  }
};
