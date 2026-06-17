import { useNavigate } from "react-router-dom";

const Navbar = () => {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token"); // ✅ remove JWT if used
    navigate("/"); // ✅ redirect
  };

  return (
    <div style={nav}>

      {/* ✅ LEFT SIDE */}
      <div style={leftSection}>
        <h2 style={logo}>FinGenie</h2>

        <span style={link} onClick={() => navigate("/dashboard")}>Home</span>
        <span style={link} onClick={() => navigate("/portfolio")}>Portfolio</span>
        <span style={link} onClick={() => navigate("/investment")}>Investment</span>
        <span style={link} onClick={() => navigate("/loan")}>Loans</span>
      </div>

      {/* ✅ RIGHT SIDE */}
      <div style={rightSection}>
        <button
          style={createBtn}
          onClick={() => navigate("/create-account")}
        >
          + Create Account
        </button>

        <button style={logoutBtn} onClick={handleLogout}>
          Logout
        </button>
      </div>

    </div>
  );
};
const nav = {
  width: "100%",
  display: "flex",
  justifyContent: "space-between",
  background: "#1f2937",
  padding: "10px 20px",
  position: "fixed",
  top: 0,
  left: 0,
  zIndex: 1000,
  flexWrap: "nowrap" 
};

const leftSection = {
  display: "flex",
  alignItems: "center",
  gap: "20px",
  color: "white",
  flexShrink: 1 
};

const rightSection = {
  display: "flex",
  gap: "10px",
  flexShrink: 0 
};


const logo = {
  marginRight: "20px",
  color: "white"
};

const link = {
  cursor: "pointer",
  fontSize: "15px"
};

const createBtn = {
  background: "#3b82f6",
  color: "white",
  border: "none",
  padding: "6px 10px",
  borderRadius: "6px",
  cursor: "pointer",
  fontSize: "14px"
};

const logoutBtn = {
  background: "#ef4444",
  color: "white",
  border: "none",
  padding: "6px 10px",
  borderRadius: "6px",
  cursor: "pointer",
  fontSize: "14px"
};


export default Navbar;
