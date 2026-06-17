import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";
import { depositMoney, withdrawMoney } from "../services/transactionService";
import CreditScoreCard from "../components/CreditScoreCard";
import Navbar from "../components/Navbar";


const Dashboard = () => {

  const [accounts, setAccounts] = useState([]);
  const [amount, setAmount] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
const [selectedAccount, setSelectedAccount] = useState(null);
const [actionType, setActionType] = useState(""); // deposit/withdraw
const [showTransferModal, setShowTransferModal] = useState(false);
const [fromAccount, setFromAccount] = useState(null);
const [toAccount, setToAccount] = useState("");

  useEffect(() => {
    fetchAccounts();
  }, []);

  const fetchAccounts = async () => {
  try {
    const res = await API.get("/accounts/my"); 
    setAccounts(res.data);
  } catch (error) {
    console.error(error);
  }
};
const handleWithdraw = async (accountId) => {
  const amount = prompt("Enter amount to withdraw");

  if (!amount) return;

  try {
    await API.post("/transactions/withdraw", {
      accountId,
      amount: Number(amount)
    });

    setMessage("Withdraw successful ");

    fetchAccounts();

    setTimeout(() => setMessage(""), 3000);
  } catch (error) {
    setMessage("Withdraw failed ");
  }
};
const handleDeposit = async (accountId) => {
  const amount = prompt("Enter amount to deposit");

  if (!amount) return;

  try {
    await API.post("/transactions/deposit", {
      accountId,
      amount: Number(amount)
    });

    setMessage("Deposit successful "); 

    fetchAccounts();

    setTimeout(() => setMessage(""), 3000); 
  } catch (error) {
    setMessage("Deposit failed "); 
  }
};
const openModal = (accountId, type) => {
  setSelectedAccount(accountId);
  setActionType(type);
  setShowModal(true);
};
const handleSubmit = async () => {
  try {

    if (actionType === "deposit") {
      await depositMoney({
        accountId: selectedAccount,
        amount: Number(amount)
      });
    }

    if (actionType === "withdraw") {
      await withdrawMoney({
        accountId: selectedAccount,
        amount: Number(amount)
      });
    }

    fetchAccounts();
    setMessage(`${actionType} successful`);

  } catch (error) {
    console.error(error);
    setMessage(`${actionType} failed `);
  }

  setShowModal(false);
  setAmount("");
};
const [transactions, setTransactions] = useState([]);
const [showTransactions, setShowTransactions] = useState(false);

const viewTransactions = async (accountId) => {
  try {
    const res = await API.get(`/transactions/account/${accountId}`);
    setTransactions(res.data);
    setShowTransactions(true);
  } catch (error) {
    console.error(error);
  }
};


const openTransferModal = (accountId) => {
  setFromAccount(accountId);
  setShowTransferModal(true);
};
const handleTransfer = async () => {
  if (!toAccount) {
    setMessage("Enter destination account ");
    return;
  }

  if (Number(toAccount) === fromAccount) {
    setMessage("Cannot transfer to same account");
    return;
  }

  if (!amount || amount <= 0) {
    setMessage("Enter valid amount ");
    return;
  }

  try {
    await API.post("/transactions/transfer", {
      fromAccountId: fromAccount,
      toAccountId: Number(toAccount),
      amount: Number(amount)
    });

    setMessage("Transfer successful");
    fetchAccounts();

  } catch (error) {
    console.error(error);
    setMessage(
      error.response?.data?.message || "Transfer failed "
    );
  }

  setShowTransferModal(false);
  setAmount("");
  setToAccount("");
};





  return (
    
  <>
    <Navbar />

    <div style={page}>
      
      <h3>Your Accounts</h3>

      {accounts.map((acc) => (
        <div key={acc.id} style={card}>
          <h4>{acc.accountType} Account</h4>
          <p><b>Account No:</b> {acc.id}</p>
          <p><b>Balance:</b> ₹ {acc.balance}</p>

          <div style={{ display: "flex", gap: "10px" }}>
            <button onClick={() => openModal(acc.id, "deposit")}>Deposit</button>
            <button onClick={() => openModal(acc.id, "withdraw")}>Withdraw</button>
            <button onClick={() => viewTransactions(acc.id)}>View Transactions</button>
            <button onClick={() => openTransferModal(acc.id)}>Transfer</button>
          </div>
          

        </div>
      ))}
      {showModal && (
  <div style={overlay}>
    <div style={modal}>
      <h3>{actionType === "deposit" ? "Deposit" : "Withdraw"}</h3>

      <input
        type="number"
        placeholder="Enter amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      <div style={{ marginTop: "10px" }}>
        <button onClick={handleSubmit}>Submit</button>
        <button onClick={() => setShowModal(false)}>Cancel</button>
      </div>
    </div>
  </div>
)}
{showTransactions && (
  <div style={overlay}>
    <div style={modal}>
      <h3>Transaction History</h3>

      {transactions.length === 0 ? (
        <p>No transactions found</p>
      ) : (
        <table style={{ width: "100%", marginTop: "10px" }}>
          <thead>
            <tr>
              <th>Amount</th>
              <th>Type</th>
              <th>Status</th>
              <th>Time</th>
            </tr>
          </thead>

          <tbody>
            {transactions.map((tx) => (
              <tr key={tx.id}>
                <td>₹ {tx.amount}</td>
                <td>{tx.type}</td>
                <td>{tx.status}</td>
                <td>{tx.timestamp}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      <button onClick={() => setShowTransactions(false)}>
        Close
      </button>
    </div>
  </div>
)}
      {showTransferModal && (
  <div style={overlay}>
    <div style={modal}>
      <h3>Transfer</h3>

      {/* ✅ TO ACCOUNT */}
      <input
        type="number"
        placeholder="Enter To Account ID"
        value={toAccount}
        onChange={(e) => setToAccount(e.target.value)}
      />

      {/* ✅ AMOUNT */}
      <input
        type="number"
        placeholder="Enter Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      <div style={{ marginTop: "10px" }}>
        <button onClick={handleTransfer}>Transfer</button>
        <button onClick={() => setShowTransferModal(false)}>Cancel</button>
      </div>
    </div>
  </div>
)}
      

    </div>
    
  </>
);
};

// ✅ styles
// ✅ styles (CLEANED)

const page = {
  paddingTop: "90px",
  paddingLeft: "20px",
  paddingRight: "20px",
  background: "#f5f7fa",
  minHeight: "100vh"
};

const card = {
  background: "white",
  padding: "15px",
  marginTop: "10px",
  borderRadius: "10px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)"
};
const overlay = {
  position: "fixed",
  top: 0,
  left: 0,
  width: "100%",
  height: "100%",
  background: "rgba(0,0,0,0.5)",
  display: "flex",
  justifyContent: "center",
  alignItems: "center"
};

const modal = {
  background: "white",
  padding: "20px",
  borderRadius: "10px"
};
export default Dashboard;