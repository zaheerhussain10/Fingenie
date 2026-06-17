
import API from "./api"; // your base axios instance

// ✅ REGISTER USER
export const registerUser = async (userData) => {
  return API.post("/users", userData);
};

// ✅ LOGIN USER
export const loginUser = async (loginData) => {
  return API.post("/users/login", loginData);
};

// ✅ GET USER BY ID
export const getUserById = async (id) => {
  return API.get(`/users/my`);
};
