// HSRP Format
// e.g. "MH 12 AB 1234", "DL-01-CJ-0007", "KA05MK123"
const HSRP_PLATE =
  /^(?:[A-Z]{2})[ -]?(?:\d{2})[ -]?(?:[A-Z]{1,2})[ -]?(?:\d{1,4})$/i;

//Bharat Series
// e.g. "21 BH 1234 AA", "24-BH-9876-ZZ", "23BH0001AB"
const BH_PLATE = /^(?:\d{2})[ -]?BH[ -]?(?:\d{4})[ -]?(?:[A-Z]{2})$/i;

// ==================== Step 1 Validation ====================//
export const validateStep1 = (values) => {
  const errors = {};

  // Vehicle number validation
  if (!values.vehicleNumber) {
    errors.vehicleNumber = "Number plate is required";
  } else if (
    HSRP_PLATE.test(values.numberPlate) ||
    BH_PLATE.test(values.numberPlate)
  ) {
    errors.vehicleNumber = "Invalid number plate format (e.g., MH12AB1234)";
  }

  // Vehicle number validation
  if (!values.vehicleColor) {
    errors.vehicleColor = "Address is required";
  } else if (!values.vehicleColor.trim()) {
    errors.vehicleColor = "Address must be at least 5 characters";
  }

  // Report type
  if (!values.reportType) {
    errors.reportType = "Address is required";
  } else if (!values.reportType.trim()) {
    errors.reportType = "Address must be at least 5 characters";
  }

  return errors;
};

// ==================== Step 1 Validation ====================//
export const validateStep2 = (values) => {
  const errors = {};

  // Vehicle number validation
  if (!values.vehicleNumber) {
    errors.vehicleNumber = "Number plate is required";
  } else if (
    HSRP_PLATE.test(values.numberPlate) ||
    BH_PLATE.test(values.numberPlate)
  ) {
    errors.vehicleNumber = "Invalid number plate format (e.g., MH12AB1234)";
  }

  // Vehicle number validation
  if (!values.vehicleColor) {
    errors.vehicleColor = "Address is required";
  } else if (!values.vehicleColor.trim()) {
    errors.vehicleColor = "Address must be at least 5 characters";
  }

  // Report type
  if (!values.reportType) {
    errors.reportType = "Address is required";
  } else if (!values.reportType.trim()) {
    errors.reportType = "Address must be at least 5 characters";
  }

  return errors;
};

// formValidation.js

export const validateStepTwo = (form) => {
  const errors = {};

  // Street Validation
  if (!form.street || form.street.trim() === "") {
    errors.street = "Street is required";
  } else if (form.street.trim().length < 3) {
    errors.street = "Street must be at least 3 characters";
  }

  // City Validation
  if (!form.city || form.city.trim() === "") {
    errors.city = "City is required";
  } else if (!/^[A-Za-z\s]+$/.test(form.city)) {
    errors.city = "City must contain only letters";
  }

  // State Validation
  if (!form.state || form.state.trim() === "") {
    errors.state = "State is required";
  } else if (!/^[A-Za-z\s]+$/.test(form.state)) {
    errors.state = "State must contain only letters";
  }

  // Pincode Validation (India format: 6 digits, not starting with 0)
  if (!form.pincode || form.pincode.trim() === "") {
    errors.pincode = "Pincode is required";
  } else if (!/^[1-9][0-9]{5}$/.test(form.pincode)) {
    errors.pincode = "Pincode must be 6 digits and cannot start with 0";
  }

  // Country Validation
  if (!form.country || form.country.trim() === "") {
    errors.country = "Country is required";
  } else if (!/^[A-Za-z\s]+$/.test(form.country)) {
    errors.country = "Country must contain only letters";
  }

  return errors;
};
