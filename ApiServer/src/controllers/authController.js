import jwt from 'jsonwebtoken';
import bcrypt from 'bcryptjs';
import { users } from '../data/db.js';
import { sendSuccess, sendError } from '../utils/response.js';

export const login = async (req, res, next) => {
  try {
    const { email, password } = req.body;

    if (!email || !password) {
      return sendError(res, 'Please provide email and password', 400);
    }

    // Normalize email
    const normalizedEmail = email.trim().toLowerCase();

    // Find user by email
    const user = users.find((u) => u.email.toLowerCase() === normalizedEmail);
    if (!user) {
      return sendError(res, 'Invalid credentials', 401);
    }

    // Verify password
    const isMatch = await bcrypt.compare(password, user.password);
    if (!isMatch) {
      return sendError(res, 'Invalid credentials', 401);
    }

    // Generate token valid for 2 days
    const token = jwt.sign(
      { id: user.id, email: user.email, role: user.role },
      process.env.JWT_SECRET || 'your_super_secret_jwt_key_here',
      { expiresIn: '2d' }
    );

    // Return user details without password
    const userResponse = {
      id: user.id,
      email: user.email,
      role: user.role,
      name: user.name
    };

    return sendSuccess(res, 'Login successful', {
      user: userResponse,
      token
    });
  } catch (error) {
    next(error);
  }
};

export const register = async (req, res, next) => {
  try {
    const { email, password, role, name } = req.body;

    if (!email || !password || !role) {
      return sendError(res, 'Please provide email, password, and role', 400);
    }

    const normalizedEmail = email.trim().toLowerCase();

    // Check if user already exists
    if (users.some((u) => u.email.toLowerCase() === normalizedEmail)) {
      return sendError(res, 'User already exists', 400);
    }

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(password, salt);

    const newUser = {
      id: `u-${Date.now()}`,
      email: normalizedEmail,
      password: hashedPassword,
      role,
      name: name || email.split('@')[0]
    };

    users.push(newUser);

    const userResponse = {
      id: newUser.id,
      email: newUser.email,
      role: newUser.role,
      name: newUser.name
    };

    return sendSuccess(res, 'User registered successfully', { user: userResponse }, 201);
  } catch (error) {
    next(error);
  }
};
