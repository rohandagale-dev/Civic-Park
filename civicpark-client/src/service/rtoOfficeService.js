import axios from "axios";

const url = import.meta.env.VITE_REST_URL + "/rto-office";

//=================== Verify RTO User ====================//
export const getUser = async () => {
  try {
    const response = await axios.post(`${url}/verify-me`, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};

export const getReports = async () => {
  try {
    const response = await axios.get(`${url}/report`, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};
