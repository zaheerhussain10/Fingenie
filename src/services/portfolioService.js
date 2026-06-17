
import API from "./api";

// ✅ Get portfolio by accountId
export const getPortfolio = (accountId) => {
  return API.get(`/portfolio/${accountId}`);
};