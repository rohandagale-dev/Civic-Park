import { useEffect, useState } from "react";
import { Input } from "../UI/Input";
import { Checkbox } from "@mui/material";
import { generatePreSignedURL, uploadImageToS3 } from "../../service/AwsService";
import { ErrorLabel } from "../shared-components/ErrorLabel";
import { validateStep1, validateStepTwo } from "../../utils/validators/reportFormValidator";
import { addNewReport } from "../../service/reportService";

const ReportForm = () => {
  const [step, setStep] = useState(1);
  const [form, setForm] = useState({
    userId: 1,
    vehicleNumber: "",
    vehicleColor: "",
    reportType: "PARKING",
    reportStatus: "VERIFIED",
    evidenceFiles: [],
  });
  const [address, setAddress] = useState({
    flatNo: "",
    streetNo: "",
    streetName: "",
    city: "",
    state: "",
    pincode: "",
    country: "India",
    longitude: "",
    latitude: "",
    district: "thane",
  });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  // ==================== Handle Input Change ====================//
  const handleChange = (e) => {
    const { name, value } = e.target;
    if (name === "vehicleNumber") {
      setForm((prev) => ({
        ...prev,
        [name]: value.toUpperCase(),
      }));
    } else {
      setForm((prev) => ({
        ...prev,
        [name]: value,
      }));
    }
  };

  // ==================== Handle File Upload ====================//
  const handleFileChange = async (e) => {
    const files = Array.from(e.target.files);

    // Generate unique file name
    const timestamp = Date.now();
    const safeName = files[0].name.replace(/\s/g, "-"); // replace spaces with hyphens
    const newFileName = `${timestamp}-${safeName}`;

    setLoading(true);
    const preSignedURL = await generatePreSignedURL(files, newFileName);

    const bucketImageURL = await uploadImageToS3(preSignedURL, files[0], newFileName)
    const newEvidenceFiles = files.map(() => ({
      mediaUrl: bucketImageURL,
      mediaType: "IMAGE",
      objectKey: newFileName,
    }));

    setForm((prev) => ({
      ...prev,
      evidenceFiles: [...prev.evidenceFiles, ...newEvidenceFiles],
    }));
  };

  // ==================== Handle Address Change ====================//
  const handleAddressChange = (e) => {
    const { name, value } = e.target;
    setAddress((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // ==================== Handle Form Steps ====================//
  const handleNext = (e) => {
    if (e) e.preventDefault();
    if (step === 1) {
      const validationErrors = validateStep1(form);
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors);
        return;
      }
    } else if (step === 2) {
      // const validationErrors = validateStepTwo(address);
      // if (Object.keys(validationErrors).length > 0) {
      //   setErrors(validationErrors);
      //   return;
      // }
    }
    setErrors({});
    setStep((step) => step + 1);
  };

  const handlePrevious = (e) => {
    if (e) e.preventDefault();
    setStep((step) => (step === 1 ? step : step - 1));
  };

  // ==================== Handle Form Submit ====================//
  const handleSubmit = async (e) => {
    e.preventDefault();

    const reportPayload = {
      userId: 3,
      reportStatus: "VERIFIED",
      verifiedBy: 999,
      reportType: "PARKING",
      vehicleNumber: form.vehicleNumber,
      vehicleColor: form.vehicleColor,
      address: address,
      evidences: form.evidenceFiles.map((file) => ({
        mediaUrl: file.mediaUrl,
        mediaType: file.mediaType,
        objectKey: file.objectKey,
      })),
    };
    // Optionally validate here too
    console.log(reportPayload);
    const id = localStorage.getItem("userId");
    const response = await addNewReport(reportPayload, id);
    console.log(response);
    // Optionally handle response, reset, close form
  };

  // ==================== Use Effect ====================//
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setAddress((prev) => ({
            ...prev,
            latitude: position.coords.latitude.toString(),
            longitude: position.coords.longitude.toString(),
          }));
        },
        (error) => {
          console.error("Error getting location:", error.message);
        }
      );
    }
  }, []);

  return (
    <form onSubmit={handleSubmit}>
      {/* ==================== Step 1 ==================== */}
      {step === 1 && (
        <div className="flex flex-col gap-4 w-96">
          <h2 className="text-xl font-bold mb-4">Vehicle Information</h2>
          <Input
            label="Vehicle Number"
            name="vehicleNumber"
            value={form.vehicleNumber}
            onChange={handleChange}
          />
          {errors.vehicleNumber && <ErrorLabel text={errors.vehicleNumber} />}
          <label className="text-sm -mb-2">Parking Type</label>
          <select
            name="reportType"
            value={form.reportType}
            onChange={handleChange}
            className="rounded-lg ring-1 h-10 px-2 ring-gray-400 focus:outline-none focus:ring-2 focus:ring-[#673AB7] w-full"
          >
            <option value="">Select Type</option>
            <option value="Parking">Parking</option>
            <option value="Abandoned">Abandoned</option>
          </select>
          {errors.reportType && <ErrorLabel text={errors.reportType} />}
          <Input
            label="Vehicle Color"
            name="vehicleColor"
            value={form.vehicleColor}
            onChange={handleChange}
          />
          {errors.vehicleColor && <ErrorLabel text={errors.vehicleColor} />}
          <div className="flex justify-end">
            <button type="button" name="Next" onClick={handleNext}>
              Next
            </button>
          </div>
        </div>
      )}

      {/* ==================== Step 2 ==================== */}
      {step === 2 && (
        <div className="flex flex-col gap-4">
          <h2 className="text-xl font-bold mb-4">Address</h2>
          <div className="flex flex-row gap-4">
            <div className="flex flex-col">
              <Input
                label="Flat No / Shop No / House No"
                name="flatNo"
                value={address.flatNo}
                onChange={handleAddressChange}
              />
              {errors.flatNo && <ErrorLabel text={errors.flatNo} />}
              <Input
                label="Street No"
                name="streetNo"
                value={address.streetNo}
                onChange={handleAddressChange}
              />
              {errors.streetNo && <ErrorLabel text={errors.streetNo} />}
              <Input
                label="Street Name"
                name="streetName"
                value={address.streetName}
                onChange={handleAddressChange}
              />
              {errors.streetName && <ErrorLabel text={errors.streetName} />}
            </div>
            <div className="flex flex-col">
              <Input label="City" name="city" value={address.city} onChange={handleAddressChange} />
              {errors.city && <ErrorLabel text={errors.city} />}
              <Input
                label="State"
                name="state"
                value={address.state}
                onChange={handleAddressChange}
              />
              {errors.state && <ErrorLabel text={errors.state} />}
              <Input
                label="Pincode"
                name="pincode"
                value={address.pincode}
                onChange={handleAddressChange}
              />
              {errors.pincode && <ErrorLabel text={errors.pincode} />}
              <Input
                label="Country"
                name="country"
                value={address.country}
                onChange={handleAddressChange}
              />
              {errors.country && <ErrorLabel text={errors.country} />}
            </div>
          </div>
          <div>
            <Checkbox /> <span className="text-sm text-gray-500 mt-2">Same as my address</span>
          </div>
          <div className="flex flex-row justify-between">
            <button type="button" name="Back" onClick={handlePrevious}>
              Back
            </button>
            <button type="button" name="Next" onClick={handleNext}>
              Next
            </button>
          </div>
        </div>
      )}

      {/* ==================== Step 3 ==================== */}
      {step === 3 && (
        <div className="flex flex-col gap-4 w-96">
          <h2 className="text-lg font-bold mb-4">Step 3: Upload Evidence</h2>
          <input
            type="file"
            accept="image/*"
            multiple
            onChange={handleFileChange}
            className="border rounded-lg p-2 w-full"
          />
          {errors.evidenceFiles && <ErrorLabel text={errors.evidenceFiles} />}
          <div className="text-sm text-gray-500 mt-1">
            {form.evidenceFiles.length} image(s) selected
          </div>
          <div className="flex justify-between mt-4">
            <button type="button" name="Back" onClick={handlePrevious}>
              Back
            </button>
            <button type="submit" name="Submit Report">
              Submit
            </button>
          </div>
          <div className={`fixed top-8 right-8 ${loading ? "block" : "hidden"}`}>
            <div className="h-6 w-6 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
          </div>
        </div>
      )}
    </form>
  );
};

export default ReportForm;
