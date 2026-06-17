import React, { useState } from "react";
import { createAccount } from "../services/accountService";
import { useNavigate } from "react-router-dom";


const CreateAccount = () => {

  const [formData, setFormData] = useState({
    balance: "",
    accountType: "SAVINGS"
  });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();


  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    try {
      await createAccount({
        balance: Number(formData.balance),
        accountType: formData.accountType
      });

      setMessage("Account Created ");


setTimeout(() => {
  setMessage("");
  navigate("/dashboard");
}, 1000);




    } catch (error) {
      console.error(error);
      setMessage("Failed to create account ");

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
    <div style={container}>
      <div style={card}>
        <h3>Create Bank Account</h3>

        <input
          name="balance"
          placeholder="Initial Balance"
          style={input}
          value={formData.balance}
          onChange={handleChange}
        />

        <select
          name="accountType"
          style={input}
          value={formData.accountType}
          onChange={handleChange}
        >
          <option value="SAVINGS">Savings</option>
          <option value="CURRENT">Current</option>
        </select>

        <button style={button} onClick={handleSubmit}>
          Create Account
        </button>
      </div>
    </div>
    </>
  );
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

const container = {
  height: "100vh",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  background: "#f5f7fa"
};

const card = {
  background: "white",
  padding: "30px",
  borderRadius: "15px",
  boxShadow: "0 4px 15px rgba(0,0,0,0.1)"
};

const input = {
  display: "block",
  width: "100%",
  padding: "10px",
  marginTop: "10px",
  borderRadius: "8px",
  border: "1px solid #ccc"
};

const button = {
  marginTop: "15px",
  padding: "10px",
  width: "100%",
  background: "#007bff",
  color: "white",
  border: "none",
  borderRadius: "8px"
};

export default CreateAccount;