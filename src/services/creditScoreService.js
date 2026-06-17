import API from "./api";

// ✅ Get Credit Score by userId
export const getCreditScore = (userId) => {
  return API.get(`/creditscore/${userId}`);
};
