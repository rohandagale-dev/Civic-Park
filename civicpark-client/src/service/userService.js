import axios from "axios";

const baseUrl = import.meta.VITE_REST_URL;

export const loginUser = async (data) => {
  try {
    const user = axios.post(baseUrl, data);
  } catch (error) {
    console.log(error);
  }
};
