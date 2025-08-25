import ReportTable from "../../components/Dashboard/ReportTable";
import Navbar from "../../components/Dashboard/Navbar";
import { useAccount } from "../../context/ContextProvider";
import { useEffect } from "react";
import { Sidebar } from "../../components/dashboard/Sidebar";
import { getRtoOfficeDetails } from "../../service/rtoOfficeService";
import { Outlet } from "react-router-dom";

const Dashboard = () => {
  const { account, setAccount } = useAccount();

  //================================================//
  /**
   * Get RTO office details
   */
  const getUserDetails = async () => {
    const id = localStorage.getItem("userId");
    const res = await getRtoOfficeDetails(id);
    setAccount(res);
  };

  //================================================//
  useEffect(() => {
    getUserDetails();
  }, []);

  return (
    <div className="h-screen w-full grid grid-cols-12">
      {/* Sidebar */}
      <div className="hidden md:block col-span-2">
        <Sidebar account={account} />
      </div>

      {/* Main Content */}
      <div className="col-span-10">
        {/* Navbar */}
        <div className="">
          <Navbar account={account} />
        </div>

        {/* Content Area */}
        <div className="rounded-2xl bg-primarybg p-6 ">
          {/* Reports Table */}
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Dashboard;
