// src/axiosInstance.js
import axios from "axios";

const axiosInstance = axios.create({
  baseURL: "http://localhost:8080", // backend URL
  withCredentials: true,           // send cookies automatically
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
