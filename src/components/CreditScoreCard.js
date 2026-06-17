import { useState } from "react";
import API from "../services/api";

const CreditScoreCard = () => {

  // ✅ FIX: add state
  const [score, setScore] = useState(null);
  const [rating, setRating] = useState("");

  const loadScore = async () => {
    try {
      const accountId = localStorage.getItem("accountId");

      if (!accountId) {
        console.log("No accountId found");
        return;
      }

      const res = await API.get(`/creditscore/${accountId}`);

      setScore(res.data.score);
      setRating(res.data.rating);

    } catch (error) {
      console.error(error);
    }
  };

  const getColor = () => {
    if (score >= 800) return "green";
    if (score >= 700) return "blue";
    if (score >= 600) return "orange";
    return "red";
  };

  return (
    <div style={card}>
      <h3>Credit Score</h3>

      {score !== null ? (
        <>
          <p style={{ fontSize: "28px", color: getColor() }}>
            {score}
          </p>

          <p style={{ fontWeight: "bold" }}>
            {rating}
          </p>
        </>
      ) : (
        <p>No score loaded</p>
      )}

      <button style={btn} onClick={loadScore}>
        Check Score
      </button>
    </div>
  );
};

const card = {
  background: "white",
  padding: "20px",
  borderRadius: "12px",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
  textAlign: "center"
};

const btn = {
  marginTop: "10px",
  padding: "8px 12px",
  border: "none",
  borderRadius: "6px",
  background: "#3b82f6",
  color: "white",
  cursor: "pointer"
};

export default CreditScoreCard;