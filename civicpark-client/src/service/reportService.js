import axios from "axios";

const baseURL = import.meta.env.VITE_REST_URL;

// ==============================================================//
export const addNewReport = async (data, id) => {
  try {
    const response = await axios.post(`${baseURL}/rto-office/report/post/${id}`, data, {
      withCredentials: true,
    });
    return response;
  } catch (error) {
    console.error("Error adding new report:", error);
    throw error;
  }
};

//============================================================//
/**
 * Fetch report by report_id
 *
 * @param id
 * @returns report object
 */
export const getReportById = async (id) => {
  try {
    const response = await axios.get(`${baseURL}/rto-office/report/post/get/${id}`, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    throw error;
  }
};

//====================================================================//
export const getReportByVehicleNumber = async (number) => {
  try {
    const response = await axios.get(`${baseURL}/rto-office/report/post/number/${number}`, {
      withCredentials: true,
    });
    console.log(response);
    return response;
  } catch (error) {
    throw error;
  }
};

//======================================================//
export const getReportSummary = async () => {
  try {
    const response = await axios.get(`${baseURL}/rto-office/report/count`, {
      withCredentials: true,
    });
    return response.data;
  } catch (error) {
    throw new Error("something went wrong while fetching dashboard summary");
  }
};
