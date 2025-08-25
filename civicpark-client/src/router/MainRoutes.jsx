import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/user/LoginPage";
import { HomePage } from "../pages/HomePage";
import Dashboard from "../pages/rto/Dashboard";
import { UserApp } from "../pages/user/UserApp";
import { ReportPage } from "../pages/user/ReportPage";
import RtoLoginPage from "../pages/rto/RtoLoginPage";
import { ProfilePage } from "../pages/user/ProfilePage";
import { ReportDetailPage } from "../pages/user/ReportDetailPage";
import SignUpPage from "../pages/user/SignUpPage";
import RtoRegisterPage from "../pages/rto/RtoRegisterPage";
import { PendingReportPage } from "../pages/rto/PendingReportPage";
import { DashboardOverview } from "../pages/rto/DashboardOverview";

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
    path: "/signup",
    element: <SignUpPage />,
  },
  {
    path: "/rto/signup",
    element: <RtoRegisterPage />,
  },

  //=================================================//
  {
    path: "/rto-dashboard",
    element: <Dashboard />,
    children: [
      { index: true, element: <DashboardOverview /> }, // 👈 auto-load Overview
      { path: "overview", element: <Dashboard /> },
      { path: "pending", element: <PendingReportPage /> },
      // { path: "pending", element: <PendingReports /> },
      // { path: "past", element: <PastReports /> },
    ],
  },
  { path: "/rto-dashboard/report/:id", element: <ReportDetailPage /> },
  {
    path: "/rto-user",
    element: <UserApp />,
  },
  {
    path: "/rto-user/report/",
    element: <ReportPage />,
  },
  {
    path: "/rto/login",
    element: <RtoLoginPage />,
  },

  {
    path: "/rto-user/profile",
    element: <ProfilePage />,
  },
]);

export default router;
