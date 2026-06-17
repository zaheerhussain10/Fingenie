

// ✅ attach JWT automatically
import axios from "axios";

const API = axios.create({
  baseURL: "http://localhost:8083"
});

API.interceptors.request.use((req) => {
  const token = localStorage.getItem("token");

  // ✅ IMPORTANT: SKIP LOGIN REQUEST
  if (
    token &&
    token !== "undefined" &&
    req.url !== "/users/login"   // ✅ STRICT CHECK
  ) {
    req.headers.Authorization = `Bearer ${token}`;
  }

  return req;
});

export default API;