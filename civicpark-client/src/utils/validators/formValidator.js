// Regex patterns
const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

// Example password regex: at least 6 characters, one letter and one number.
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{6,}$/;

export const emailValidator = (email) => {
  return emailRegex.test(email);
};

export const passwordValidator = (password) => {
  return passwordRegex.test(password)
};
