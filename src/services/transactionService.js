
import API from "./api";

// ✅ Deposit
export const depositMoney = (data) => {
  return API.post("/transactions/deposit", data);
};

// ✅ Withdraw
export const withdrawMoney = (data) => {
  return API.post("/transactions/withdraw", data);
};

// ✅ Transfer
export const transferMoney = (data) => {
  return API.post("/transactions/transfer", data);
};
