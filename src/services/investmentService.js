
import API from "./api";

// ✅ Create Investment
export const createInvestment = (data) => {
  return API.post("/investments", data);
};

// ✅ Get Investments by Account
export const getInvestmentsByAccount = (accountId) => {
  return API.get(`/investments/account/${accountId}`);
};