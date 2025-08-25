import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAccount } from "../context/ContextProvider";
import { verifyUser } from "../service/userService";

const ProtectedRoutes = ({ children }) => {
  // const navigate = useNavigate();
  // const { account, setAccount } = useAccount();

  // useEffect(() => {
  //   const verifyMe = async () => {
  //     // If we already have account from login, skip API check
  //     if (account) return;

  //     try {
  //       const res = await verifyUser();
  //       if (res.status === 200) {
  //         setAccount(res); // save from backend response
  //       } else {
  //         navigate("/login");
  //       }
  //     } catch (err) {
  //       console.log("Unauthorized:", err);
  //       navigate("/login");
  //     }
  //   };

  //   verifyMe();
  // }, [account, setAccount, navigate]);

  return <>{children}</>;
};

export default ProtectedRoutes;
