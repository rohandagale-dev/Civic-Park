import axios from "axios";
import axiosInstance from "../utils/AxiosConfig";

// Correct way to use VITE environment variable
const baseURL = import.meta.env.VITE_REST_URL;
const url2 = import.meta.env.VITE_REST_URL + "/user";
axios.defaults.withCredentials = true;
const url3 = import.meta.env.VITE_REST_URL + "/public/user";

//==================== Login User ====================//
export const loginUser = async (data) => {
  try {
    const response = await axios.post(url3 + "/login", data, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};

//==================== Get User ====================//
export const getUser = async (id) => {
  try {
    const response = await axios.get(`${baseURL}/user/profile/${id}`, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
    throw error;
  }
};

//=================== Verify User ====================//
export const verifyUser = async () => {
  try {
    const response = await axios.post(`${url2}/verify-me`, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};

//=================== Logout ====================//
export const logOutUser = async () => {
  try {
    const response = await axios.post(`${baseURL}/user/logout`, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
    throw error;
  }
};
