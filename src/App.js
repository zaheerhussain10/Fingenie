import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import CreateAccount from "./pages/CreateAccount";
import LoanPage from "./pages/LoanPage";
import PortfolioPage from "./pages/PortfolioPage";
import InvestmentPage from "./pages/InvestmentPage";



function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/create-account" element={<CreateAccount />} />
        <Route path="/loan" element={<LoanPage />} />
        <Route path="/portfolio" element={<PortfolioPage />} />
        <Route path="/investment" element={<InvestmentPage />} />

        
      </Routes>
    </BrowserRouter>
  );
}

export default App;