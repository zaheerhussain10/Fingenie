
import { useState } from "react";
import { getPortfolio } from "../services/portfolioService";
import Navbar from "../components/Navbar";
import { getUserAccounts } from "../services/accountService";
import API from "../services/api"



const PortfolioPage = () => {

  const [accountId, setAccountId] = useState("");
  const [portfolio, setPortfolio] = useState(null);
  const [message, setMessage] = useState("");
  const [accounts, setAccounts] = useState([]);
  


  const handleLoad = async () => {
  try {
    const res = await getPortfolio(accountId);
    const accRes = await API.get("/accounts/my");

    setPortfolio(res.data);
    setAccounts(accRes.data);
    setMessage("");

  } catch (error) {
    console.error(error.response?.data);
    setMessage("Error loading portfolio ");
  }
};


  return (
    <>
  <Navbar />

  <div style={{ paddingTop: "90px", padding: "20px" }}>
    <h2>Portfolio</h2>

<input
  placeholder="Enter Account ID"
  value={accountId}
  onChange={(e) => setAccountId(e.target.value)}
  style={input}
/>

<button onClick={handleLoad} style={button}>
  Load Portfolio
</button>

{message && <p>{message}</p>}

    {portfolio && (

  <div style={{ marginTop: "20px" }}>

    {/* ✅ TOP CARDS */}
    <div style={topContainer}>

      {/* ✅ CREDIT SCORE */}


      {/* ✅ TOTAL BALANCE */}
      <div style={cardBox}>
        <h4>Total Balance</h4>

        <h2 style={{ color: "green" }}>
          ₹ {(accounts || []).reduce((sum, acc) => sum + acc.balance, 0)}
        </h2>

      </div>

    </div>

    {/* ✅ ACCOUNT BREAKDOWN */}
    <div style={card}>
      <h3>Account Breakdown</h3>

      {(accounts || []).map((acc) => (
  <div
    key={acc.id}
    style={{
      display: "flex",
      justifyContent: "space-between",
      padding: "10px",
      borderBottom: "1px solid #eee"
    }}
  >
    <div>
      <b>Account No:</b> {acc.id} <br/>
      <small>{acc.accountType}</small>
    </div>

    <div style={{ color: "green" }}>
      ₹ {acc.balance}
    </div>
  </div>
))}


    </div>

    {/* ✅ PORTFOLIO SUMMARY */}
    <div style={card}>
      <h3>Portfolio Summary</h3>

      <p><b>Total Investment:</b> ₹ {portfolio.totalInvestment}</p>
      <p><b>Total Returns:</b> ₹ {portfolio.totalReturns}</p>
      <p><b>No. of Investments:</b> {portfolio.numberOfInvestments}</p>

    </div>

  </div>
)}

  </div>
</>
   
  );
};
const topContainer = {
  display: "grid",
  gridTemplateColumns: "1fr 1fr",
  gap: "20px"
};

const cardBox = {
  background: "white",
  padding: "20px",
  borderRadius: "15px",
  boxShadow: "0 5px 15px rgba(0,0,0,0.1)"
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

const card = {
  background: "white",
  padding: "15px",
  marginTop: "15px",
  borderRadius: "10px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)"
};

export default PortfolioPage;
