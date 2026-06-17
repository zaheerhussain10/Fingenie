
import { useEffect, useState } from "react";
import { getMyLoans } from "../services/loanService";

const LoanList = () => {

  const [loans, setLoans] = useState([]);

  useEffect(() => {
    loadLoans();
  }, []);

  const loadLoans = async () => {
    try {
      const res = await getMyLoans();
      setLoans(res.data);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div style={{ marginTop: "20px" }}>

      <h3>Your Loans</h3>

      {loans.length === 0 ? (
        <p>No loans yet</p>
      ) : (
        loans.map((loan) => (
          <div key={loan.id} style={card}>

            <p><b>Amount:</b> ₹ {loan.amount}</p>
            <p><b>Type:</b> {loan.loanType}</p>
            <p><b>Status:</b> {loan.status}</p>

          </div>
        ))
      )}

    </div>
  );
};

export default LoanList;

const card = {
  background: "white",
  padding: "10px",
  marginTop: "10px",
  borderRadius: "8px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)"
};
