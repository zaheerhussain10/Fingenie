
import API from "./api";

// ✅ CREATE ACCOUNT
export const createAccount = async (accountData) => {
  return API.post("/accounts", accountData);
};

// ✅ GET ACCOUNT BY ID
export const getAccountById = async (id) => {
  return API.get(`/accounts/${id}`);
};

// ✅ GET USER ACCOUNTS
export const getUserAccounts = async (userId) => {
  return API.get(`/accounts/user/${userId}`);
};