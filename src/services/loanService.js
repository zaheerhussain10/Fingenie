import API from "./api";

// ✅ Apply Loan
export const applyLoan = (data) => {
  return API.post("/loans", data);
};

// ✅ Get Loan by ID
export const getLoanById = (id) => {
  return API.get(`/loans/${id}`);
};

// ✅ OPTIONAL (later add)
export const getMyLoans = () => {
  return API.get("/loans/my");
};
