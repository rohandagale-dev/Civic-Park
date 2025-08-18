import axios from "axios";

const baseURL = import.meta.env.VITE_REST_URL;

// ==================== Upload Image Service ====================//
export const uploadImageToS3 = async (file) => {
  try {
    const fileName = file[0].name;
    const contentType = file[0].type;

    const response = await axios.get(
      `${baseURL}/api/aws-s3/presigned-url`,
      {
        params: { fileName: fileName, contentType: contentType },
        withCredentials: true,
      }
    );
    return response?.data?.url;
  } catch (error) {
    console.log(error);
  }
};
