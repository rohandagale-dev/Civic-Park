import React, { useEffect, useState } from "react";
import { Input } from "../UI/Input";
import { Checkbox } from "@mui/material";
import { uploadImageToS3 } from "../../service/AwsService";
import { ErrorLabel } from "../shared-components/ErrorLabel";
import { Button } from "../UI/Button";
import {
  validateStep1,
  validateStepTwo,
} from "../../utils/validators/reportFormValidator";
import { addNewReport } from "../../service/reportService";

const ReportForm = () => {
  const [step, setStep] = useState(1);
  const [form, setForm] = useState({
    vehicleNumber: "",
    vehicleColor: "",
    reportType: "",
    longitude: "",
    latitude: "",
    street: "",
    city: "",
    state: "",
    pincode: "",
    country: "",
    address: "",
    evidenceFiles: [],
  });
  const [errors, setErrors] = useState({});

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

    const responseURL = await uploadImageToS3(files);

    const newEvidenceFiles = files.map((file) => ({
      mediaUrl: responseURL,
      mediaType: file.type,
    }));

    setForm((prev) => ({
      ...prev,
      evidenceFiles: [...prev.evidenceFiles, ...newEvidenceFiles],
    }));
  };

  // ==================== Handle Input Validation ====================//

  // ==================== Handle Form Steps ====================//
  const handleNext = () => {
    if (step == 1) {
      const validationErrors = validateStep1(form);
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors);
        return;
      }
    } else if (step == 2) {
      const validationErrors = validateStepTwo(form);
      if (Object.keys(validationErrors).length > 0) {
        setErrors(validationErrors);
        return;
      }
    }
    console.log("validated");
    setStep((step) => step + 1);
  };

  const handlePrevious = () => {
    setStep((step) => (step === 1 ? (step = step) : step - 1));
  };

  // ==================== Handle Form Submit ====================//
  const handleSubmit = async (e) => {
    e.preventDefault();

    const reportPayload = {
      reportType: form.reportType,
      vehicleNumber: form.vehicleNumber,
      vehicleColor: form.vehicleColor,
      city: form.city,
      street: form.street,
      state: form.state,
      pincode: form.pincode,
      country: form.country,
      longitude: "12.2333",
      latitude: "12.33455",

      evidences: form.evidenceFiles.map((file) => ({
        mediaUrl: file.mediaUrl,
        mediaType: file.mediaType,
      })),
    };
    console.log(reportPayload);
    const id = localStorage.getItem("userId");
    const response = await addNewReport(reportPayload, id);
    console.log(response);
  };

  // ==================== Use Effect ====================//
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          setForm((prev) => ({
            ...prev,
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
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
            <button name="Next" onClick={handleNext}>
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
                label="Street"
                name="street"
                value={form.street}
                onChange={handleChange}
              />
              {errors.street && <ErrorLabel text={errors.street} />}
              <Input
                label="City"
                name="city"
                value={form.city}
                onChange={handleChange}
              />
              {errors.city && <ErrorLabel text={errors.city} />}
              <Input
                label="State"
                name="state"
                value={form.state}
                onChange={handleChange}
              />
              {errors.state && <ErrorLabel text={errors.state} />}
            </div>
            <div className="flex flex-col">
              <Input
                label="Pincode"
                name="pincode"
                value={form.pincode}
                onChange={handleChange}
              />
              {errors.pincode && <ErrorLabel text={errors.pincode} />}
              <Input
                label="Country"
                name="country"
                value={form.country}
                onChange={handleChange}
              />
              {errors.country && <ErrorLabel text={errors.country} />}
            </div>
          </div>
          <div>
            <Checkbox />{" "}
            <span className="text-sm text-gray-500 mt-2">
              Same as my address
            </span>
          </div>
          <div className="flex flex-row justify-between">
            <button name="Back" onClick={handlePrevious}>
              Back
            </button>
            <button name="Next" onClick={handleNext}>
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
            <button name="Back" onClick={handlePrevious}>
              Back
            </button>
            <button name="Submit Report" type="submit" onClick={handleSubmit}>
              Submit
            </button>
          </div>
        </div>
      )}
    </form>
  );
};

export default ReportForm;
