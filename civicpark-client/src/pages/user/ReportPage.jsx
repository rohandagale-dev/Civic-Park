import React from "react";
import ReportForm from "../../components/User/ReportForm";

export const ReportPage = () => {


  
  return (
    <div className="flex flex-col items-center justify-center h-screen w-full gap-6">
      <div>
        <p className="text-lg">File an Report against Unauthorize Parking</p>
      </div>
      <div className="flex items-center justify-center  py-16 px-32 rounded-lg w-[80%]">
        <ReportForm />
      </div>
    </div>
  );
};
