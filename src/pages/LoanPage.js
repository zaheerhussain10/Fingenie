import React, { useState } from "react";
import { applyLoan } from "../services/loanService";
import Navbar from "../components/Navbar";


const LoansPage = () => {

  const [message, setMessage] = useState("");

  // ✅ store amount per loan type
  const [amounts, setAmounts] = useState({
    CAR_LOAN: 100000,
    HOME_LOAN: 500000,
    PERSONAL_LOAN: 50000,
    EDUCATION_LOAN: 150000
  });

  const handleChange = (type, value) => {
    setAmounts({ ...amounts, [type]: value });
  };

  const handleApply = async (loanType) => {
    try {
      const accountId = localStorage.getItem("accountId");

      const data = {
        accountId: Number(accountId),
        loanType: loanType,
        amount: Number(amounts[loanType])
      };

      await applyLoan(data);

      setMessage(`${loanType} applied successfully ✅`);
      setTimeout(() => setMessage(""), 1000);

    } catch (error) {
      setMessage("Loan Failed ❌");
      setTimeout(() => setMessage(""), 1500);
    }
  };

  const loans = [
    { type: "CAR_LOAN", title: "Car Loan 🚗", rate: "8.5%", tenure: "5 Years" },
    { type: "HOME_LOAN", title: "Home Loan 🏠", rate: "7.2%", tenure: "20 Years" },
    { type: "PERSONAL_LOAN", title: "Personal Loan 💼", rate: "10.5%", tenure: "3 Years" },
    { type: "EDUCATION_LOAN", title: "Education Loan 🎓", rate: "6.8%", tenure: "10 Years" }
  ];

  return (
    <>
     <Navbar />

    <div style={page}>
      {/* ✅ Toast */}
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

      <div className="container mt-4">
        <h2 className="text-center mb-4">Select Loan</h2>

        <div className="row">
          {loans.map((loan) => (
            <div key={loan.type} className="col-md-3 mb-4">

              <div className="card p-4 shadow rounded-4 text-center">

                <h5>{loan.title}</h5>

                <p><b>Interest:</b> {loan.rate}</p>
                <p><b>Tenure:</b> {loan.tenure}</p>

                {/* ✅ Amount input */}
                <input
                  type="number"
                  className="form-control mt-2"
                  value={amounts[loan.type]}
                  onChange={(e) =>
                    handleChange(loan.type, e.target.value)
                  }
                />

                {/* ✅ Apply button */}
                <button
                  className="btn btn-primary mt-3 w-100"
                  onClick={() => handleApply(loan.type)}
                >
                  Apply
                </button>

              </div>

            </div>
          ))}
        </div>
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
};
const page = {
  paddingTop: "90px",
  paddingLeft: "20px",
  paddingRight: "20px",
  background: "#f5f7fa",
  minHeight: "100vh"
};

export default LoansPage;
