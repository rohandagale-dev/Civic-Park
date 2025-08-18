import { Input } from "../../components/UI/Input";
import { Button } from "../../components/UI/Button";

import ReportTable from "../../components/Dashboard/ReportTable";
import Navbar from "../../components/Dashboard/Navbar";
import { useEffect } from "react";
import { getReports } from "../../service/rtoOfficeService";
import { useAccount } from "../../context/ContextProvider";

const Dashboard = () => {
  const {account} = useAccount();

  console.log("account" + account);

  return (
    <div className="flex justify-center flex-col gap-4 items-center">
      <div>
        <Navbar />
      </div>
      <div className="flex flex-row items-center justify-center gap-2">
        <div className="w-60">
          <Input id="search" name="search" type="text" placeholder="Search" />
        </div>
        <div className="pt-1">
          <Button>Search</Button>
        </div>
      </div>
      {/* =========================================== */}
      <div className="w-[90%]">
        <ReportTable />
      </div>
    </div>
  );
};

export default Dashboard;
