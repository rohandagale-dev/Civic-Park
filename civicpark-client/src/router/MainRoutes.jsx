import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/LoginPage";
import { HomePage } from "../pages/HomePage";
import Dashboard from "../pages/Dashboard/Dashboard";
import ProtectedRoutes from "./ProtectedRoutes";

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
    path: "/dashboard",
    element: (
      <ProtectedRoutes>
        <Dashboard />
      </ProtectedRoutes>
    ),
  },
]);

export default router;
