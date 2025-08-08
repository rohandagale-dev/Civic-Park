import axios from "axios";

// Correct way to use VITE environment variable
const url = import.meta.env.VITE_REST_URL + "/public/login";

console.log(url);

export const loginUser = async (data) => {
  try {
    const response = await axios.post(url, data, { withCredentials: true });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
    throw error; // forward error to the caller
  }
};
