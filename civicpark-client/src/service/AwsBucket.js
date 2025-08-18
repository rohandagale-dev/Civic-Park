import AWS from "aws-sdk";

AWS.config.update({
  accessKeyId: import.meta.env.VITE_AWS_ACCESSKEY_ID, // Never hardcode in production
  secretAccessKey: import.meta.env.VITE_AWS_SECRET_ACCESSKEY,
  region: import.meta.env.VITE_AWS_REGION,
});

const s3 = new AWS.S3();

export default s3;

