import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/user/LoginPage";
import { HomePage } from "../pages/HomePage";
import Dashboard from "../pages/rto/Dashboard";
import ProtectedRoutes from "./ProtectedRoutes";
import { UserApp } from "../pages/user/UserApp";
import { ReportPage } from "../pages/user/ReportPage";
import RtoLoginPage from "../pages/rto/RtoLoginPage";
import { ProfilePage } from "../pages/user/ProfilePage";
import ContextProvider from "../context/ContextProvider";

const router = createBrowserRouter([
  {
    path: "/",
    element: <HomePage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/rto/login",
    element: <RtoLoginPage />,
  },
  {
    path: "/rto-dashboard",
    element: (
      <ProtectedRoutes>
        <Dashboard />
      </ProtectedRoutes>
    ),
  },
  {
    path: "/rto-user",
    element: <UserApp />,
  },
  {
    path: "/rto-user/report",
    element: <ReportPage />,
  },
  {
    path: "/rto-user/profile",
    element: <ProfilePage />,
  },
]);

export default router;
