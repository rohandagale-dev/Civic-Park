import axios from "axios";

const url = import.meta.env.VITE_REST_URL;

//=============================================//
/**
 * Get all reports
 *
 * @returns {} list of reports
 */
export const getReports = async () => {
  try {
    const response = await axios.get(`${url}/rto-office/report`, {
      withCredentials: true,
    });
    console.log(response);
    return response;
  } catch (error) {
    console.error("Login failed:", error);
  }
};

//========================================================//
/**
 * Login RTO office with given credentials
 *
 * @async
 * @param {Object} data - Login credentials (e.g., { email, password })
 * @returns {Promise<Object>} - The RTO office data from backend
 * @throws {Error} If login fails
 */
export const loginRtoOffice = async (data) => {
  try {
    const response = await axios.post(`${url}/public/rto/login`, data, {
      withCredentials: true,
    });
    console.log(response);
    return response;
  } catch (error) {
    console.error("Login error:", error);
    throw new Error("Error while logging in RTO office");
  }
};

//============================================================//
/**
 *Fetch RTO account details by Id
 *
 * @param {int} id
 * @returns {object} RTO account details
 */
export const getRtoOfficeDetails = async (id) => {
  try {
    const response = await axios.get(`${url}/rto-office/get-details/${id}`, {
      withCredentials: true,
    });
    console.log(response.data)
    return response.data;
  } catch (error) {
    console.log(error);
    throw new Error("Error while fetching rto office account details");
  }
};
