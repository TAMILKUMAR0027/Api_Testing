import bcrypt from 'bcryptjs';

// Pre-calculate bcrypt hash for the preloaded test user
const salt = bcrypt.genSaltSync(10);
const adminHashedPassword = bcrypt.hashSync("123", salt);

export const users = [
  {
    id: "u-1",
    email: "sam@gmail.com",
    password: adminHashedPassword,
    role: "admin",
    name: "Sam Admin"
  }
];

export const notes = [];

export const institutions = [
  {
    id: "inst-1",
    name: "Massachusetts Institute of Technology",
    code: "MIT",
    location: "Cambridge, USA"
  },
  {
    id: "inst-2",
    name: "Stanford University",
    code: "STANFORD",
    location: "Stanford, USA"
  },
  {
    id: "inst-3",
    name: "Harvard University",
    code: "HARVARD",
    location: "Cambridge, USA"
  }
];

export const roles = [
  {
    id: "role-1",
    name: "admin",
    description: "System administrator with full access to all resources"
  },
  {
    id: "role-2",
    name: "instructor",
    description: "Course instructor who can manage lectures and assignments"
  },
  {
    id: "role-3",
    name: "student",
    description: "Student enrolled in courses to view content and complete tasks"
  }
];

export const courses = [
  {
    id: "course-1",
    title: "Introduction to Web Development",
    code: "CS-101",
    department: "Computer Science",
    credits: 4
  },
  {
    id: "course-2",
    title: "REST API Design and Automation",
    code: "CS-302",
    department: "Software Engineering",
    credits: 3
  },
  {
    id: "course-3",
    title: "Data Structures & Algorithms",
    code: "CS-201",
    department: "Computer Science",
    credits: 4
  }
];
