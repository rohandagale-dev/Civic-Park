import React, { useEffect, useState } from "react";
import { getUser } from "../../service/userService";
import { Button } from "../../components/UI/Button";

export const ProfilePage = () => {
  const [data, setData] = useState();

  useEffect(() => {
    const getUserDetails = async () => {
      const response = await getUser(3);
      setData(response.data);
    };

    getUserDetails();
  }, []);
  console.log(data);

  return (
    <div className="flex flex-col h-screen w-full p-16 gap-8">
      <div className="flex flex-row gap-8">
        <p className="font-bold">
          Name:{" "}
          <span className="font-normal">
            {data?.firstName} {data?.middleName} {data?.lastName}
          </span>
        </p>
        <p className="font-bold">
          Contact No: <span className="font-normal">{data?.contactNumber}</span>
        </p>
      </div>
      {/* <div className="flex flex-col gap-2">
        <div>
          <p className="font-bold">Address: </p>
        </div>
        <div className="flex flex-row gap-8">
          <p className="font-bold">
            Flat No:{" "}
            <span className="font-normal">{data?.address?.flatNo}</span>
          </p>
          <p className="font-bold">
            Street No:{" "}
            <span className="font-normal">{data?.address?.streetNo}</span>
          </p>
          <p className="font-bold">
            Street Name:{" "}
            <span className="font-normal">{data?.address?.streetName}</span>
          </p>
        </div>
        <div className="flex flex-row gap-8">
          <p className="font-bold">
            District:{" "}
            <span className="font-normal">{data?.address?.district}</span>
          </p>
          <p className="font-bold">
            State: <span className="font-normal">{data?.address?.state}</span>
          </p>
          <p className="font-bold">
            Pincode:{" "}
            <span className="font-normal">{data?.address?.pincode}</span>
          </p>
        </div>
      </div> */}
      <div className="w-68">
        <span className="font-bold">Address: </span>
        Room No: {data?.address?.flatNo}, Sai Vallhab Residency{", "} <br />{" "}
        Street No: {data?.address?.streetNo}, {data?.address?.streetName}
        {", "}
        <br />
        {data?.address?.district}
        {", "}
        {data?.address?.state}
        {", "}Pincode: {data?.address?.pincode}
      </div>
      <div>
            
      </div>
    </div>
  );
};
