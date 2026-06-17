import React, { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import AuthLayout from "../components/AuthLayout";

const Login = () => {

  const navigate = useNavigate();

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");


  const handleLogin = async () => {
  try {
    
    const response = await API.post("/users/login", {
      email,
      password
    });
    console.log("Login Response:", response.data);
    localStorage.setItem("token", response.data);
    

  // show toast
    setMessage("Login Successful");

  // auto redirect after 1 sec
    setTimeout(() => {
      setMessage("");
      navigate("/dashboard");
    }, 1000);

  } catch (error) {

      setMessage("Login Failed ");

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

    <h2 style={{ textAlign: "center" }}>FinGenie</h2>
    <p style={{ textAlign: "center", color: "#888" }}>Welcome back</p>

    <input
  style={inputStyle}
  placeholder="Email"
  value={email}
  onChange={(e) => setEmail(e.target.value)}
/>

<input
  style={inputStyle}
  type="password"
  placeholder="Password"
  value={password}
  onChange={(e) => setPassword(e.target.value)}
/>

    <button style={buttonStyle} onClick={handleLogin}>
      Login
    </button>

    <p style={{ textAlign: "center", marginTop: "20px" }}>
      Don’t have an account?{" "}
      <span onClick={() => navigate("/register")} style={{ color: "#007bff", cursor: "pointer" }}>
        Create Account
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
  border: "1px solid #ddd",
  outline: "none",
  fontSize: "14px"
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
export default Login;