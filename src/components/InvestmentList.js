
import { useState, useEffect } from "react";
import { getInvestmentsByAccount } from "../services/investmentService";

const InvestmentList = ({ accountId }) => {

  const [investments, setInvestments] = useState([]);

  useEffect(() => {
    if (accountId) {
      loadInvestments();
    }
  }, [accountId]);

  const loadInvestments = async () => {
    try {
      const res = await getInvestmentsByAccount(accountId);
      setInvestments(res.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>

      <h3>Your Investments</h3>

      {!accountId ? (
        <p>Enter account ID to view investments</p>
      ) : investments.length === 0 ? (
        <p>No investments yet</p>
      ) : (
        investments.map((inv) => (
          <div key={inv.id} style={card}>
            <p><b>Type:</b> {inv.type}</p>
            <p><b>Amount:</b> ₹ {inv.amount}</p>
            <p><b>Returns:</b> ₹ {inv.returns}</p>
          </div>
        ))
      )}

    </div>
  );
};

export default InvestmentList;

const card = {
  background: "white",
  padding: "12px",
  marginTop: "10px",
  borderRadius: "8px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)"
};