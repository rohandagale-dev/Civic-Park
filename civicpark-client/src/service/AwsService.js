import axios from "axios";

const baseURL = import.meta.env.VITE_REST_URL;

// ========================================================//
/**
 * Generate pre-signed url that used to upload image to S3
 *
 * @param  file
 * @param newFileName
 * @returns Pre-signed url
 */
export const generatePreSignedURL = async (file, newFileName) => {
  try {
    const contentType = file[0].type;

    const response = await axios.get(`${baseURL}/api/aws-s3/presigned-url`, {
      params: { fileName: newFileName, contentType: contentType },
      withCredentials: true,
    });
    console.log(response);
    return response?.data?.url;
  } catch (error) {
    console.log(error);
  }
};

// ==================================================================//
/**
 *Upload image to S3 bucket using pre-signed url
 *
 * @param  preSignedURL
 * @param  file
 * @param objectKey
 */
export const uploadImageToS3 = async (preSignedURL, file, objectKey) => {
  try {
    const contentType = file.type;

    await axios.put(preSignedURL, file, {
      headers: { "Content-Type": contentType },
    });

    const bucketImageURL = `https://rto-system.s3.ap-south-1.amazonaws.com/${objectKey}`;
    console.log(bucketImageURL)
    return bucketImageURL;
  } catch (error) {
    console.error("Failed to upload image to S3:", error);
    throw new Error("Failed to upload image to S3");
  }
};
