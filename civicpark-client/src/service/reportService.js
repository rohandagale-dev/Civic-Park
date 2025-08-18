import axios from "axios";

const baseURL = import.meta.env.VITE_REST_URL;
console.log(baseURL)
// ==================== Add New Report ====================//
export const addNewReport = async (data, id, params = {}) => {
  try {
    const response = await axios.post(
      `${baseURL}/rto-office/report/post/${id}`, // use baseURL
      data,
      {
        params, // query params (optional)
        withCredentials: true,
      }
    );
    return response; // return only the useful data
  } catch (error) {
    console.error("Error adding new report:", error);
    throw error; // allow caller to handle the error
  }
};
