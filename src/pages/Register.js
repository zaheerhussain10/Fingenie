import React, { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import AuthLayout from "../components/AuthLayout";

const Register = () => {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
    role: "USER" 
  });
  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleRegister = async () => {
    try {
      await API.post("/users", formData);

     setMessage("Account Created");

setTimeout(() => {
  setMessage("");
  navigate("/"); // go to login
}, 1000);

    } catch (error) {
  console.error(error.response?.data); 
  setMessage("Registration Failed ");

setTimeout(() => {
  setMessage("");
}, 1500);

}
  };

  return (
    <>
  {message && (
    <div
      style={{
        ...toast,
        background: message.includes("Failed") ? "#ef4444" : "#16a34a"
      }}
    >
      {message}
    </div>
  )}
    <AuthLayout>

      <h2 style={{ textAlign: "center" }}>Create Account</h2>

      <input
        style={inputStyle}
        name="name"
        placeholder="Full Name"
        value={formData.name}
        onChange={handleChange}
      />

      <input
        style={inputStyle}
        name="email"
        placeholder="Email"
        value={formData.email}
        onChange={handleChange}
      />

      <input
        style={inputStyle}
        name="phone"
        placeholder="Phone (10 digits)"
        value={formData.phone}
        onChange={handleChange}
      />

      <input
        style={inputStyle}
        name="password"
        type="password"
        placeholder="Password"
        value={formData.password}
        onChange={handleChange}
      />

      {/* ✅ Role dropdown */}
      <select
        style={inputStyle}
        name="role"
        value={formData.role}
        onChange={handleChange}
      >
        <option value="USER">USER</option>
        <option value="ADMIN">ADMIN</option>
      </select>

      <button style={buttonStyle} onClick={handleRegister}>
        Register
      </button>

      <p style={{ textAlign: "center", marginTop: "20px" }}>
        Already have an account?{" "}
        <span
          style={{ color: "#007bff", cursor: "pointer" }}
          onClick={() => navigate("/")}
        >
          Login
        </span>
      </p>

    </AuthLayout>
    </>
  );
};

// ✅ styles
const inputStyle = {
  width: "100%",
  padding: "12px",
  marginTop: "15px",
  borderRadius: "10px",
  border: "1px solid #ddd"
};

const buttonStyle = {
  width: "100%",
  marginTop: "20px",
  padding: "12px",
  borderRadius: "10px",
  border: "none",
  backgroundColor: "#007bff",
  color: "white",
  fontWeight: "bold",
  cursor: "pointer"
};
const toast = {
  position: "fixed",
  top: "20px",
  right: "20px",
  color: "white",
  padding: "12px 20px",
  borderRadius: "12px",
  boxShadow: "0 5px 15px rgba(0,0,0,0.2)",
  zIndex: 1000,
  fontWeight: "bold",
  transition: "all 0.3s ease"
};

export default Register;