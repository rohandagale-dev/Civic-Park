import { createContext, useState, useContext } from "react";

// Create context with default value null
const AccountContext = createContext(null);

// Custom hook to use context
export const useAccount = () => {
  const context = useContext(AccountContext);
  if (!context) {
    throw new Error("useAccount must be used within a ContextProvider");
  }
  return context;
};

// Provider component
const ContextProvider = ({ children }) => {
  const [account, setAccount] = useState(null);
  const [reportData, setReportData] = useState(null);

  const value = { account, setAccount, reportData, setReportData };

  return (
    <AccountContext.Provider value={value}>
      {children}
    </AccountContext.Provider>
  );
};

export default ContextProvider;
