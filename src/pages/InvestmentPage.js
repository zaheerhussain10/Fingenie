import { useState } from "react";
import { createInvestment } from "../services/investmentService";
import InvestmentList from "../components/InvestmentList";
import Navbar from "../components/Navbar";

const InvestmentPage = () => {

  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState("");
  const [returns, setReturns] = useState("");
  const [type, setType] = useState("");
  const [message, setMessage] = useState("");

  const handleCreate = async () => {
    try {

      await createInvestment({
        accountId: Number(accountId),
        amount: Number(amount),
        returns: Number(returns),
        type
      });

      setMessage("Investment Created ");

    } catch (error) {
      setMessage(error.response?.data || "Failed ");
    }
  };

  return (
    <>
  <Navbar />

  <div style={{ paddingTop: "90px", padding: "20px" }}>
    {/* Your page content */}
     <div style={container}>

      <h2>Create Investment</h2>

      <input
        placeholder="Account ID"
        value={accountId}
        onChange={(e) => setAccountId(e.target.value)}
        style={input}
      />

      <input
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        style={input}
      />

      <input
        placeholder="Returns"
        value={returns}
        onChange={(e) => setReturns(e.target.value)}
        style={input}
      />

      <select
        value={type}
        onChange={(e) => setType(e.target.value)}
        style={input}
      >
        <option value="">Select Type</option>
        <option value="STOCK">Stock</option>
        <option value="MUTUAL_FUND">Mutual Fund</option>

      </select>

      <button onClick={handleCreate} style={button}>
        Invest
      </button>

      {message && <p>{message}</p>}

      {/* ✅ LIST BELOW */}
      <InvestmentList accountId={accountId} />

    </div>
  </div>
</>
    
   
  );
};

const container = {
  maxWidth: "400px",
  margin: "40px auto",
  display: "flex",
  flexDirection: "column",
  gap: "10px"
};

const input = {
  padding: "10px",
  borderRadius: "8px",
  border: "1px solid #ccc"
};

const button = {
  padding: "10px",
  background: "#10b981",
  color: "white",
  border: "none",
  borderRadius: "8px",
  cursor: "pointer"
};

export default InvestmentPage;