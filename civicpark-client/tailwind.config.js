const { colors } = require("@mui/material");

module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./node_modules/@mui/material/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        primary: "#673AB7", // primary text color purple
        primarytext: "#1a1a1a",
        secondarytext:"#F8F8F8"
      },
    },
  },
  plugins: [],
};
