import axios from "axios";

const baseURL = import.meta.env.VITE_REST_URL;

//==================== Login User ====================//
export const loginUser = async (data) => {
  try {
    const response = await axios.post(`${baseURL}/public/user/login`, data, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};

//==========================================================//
/**
 * Handle user sign up
 *
 * @param data
 */
export const signUpUser = async (data) => {
  try {
    const response = await axios.post(`${baseURL}/public/user/signup`, data, {
      withCredentials: false,
    });
    console.log(response)
    return response.status;
  } catch (error) {
    throw new Error("Error while creating user");
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
