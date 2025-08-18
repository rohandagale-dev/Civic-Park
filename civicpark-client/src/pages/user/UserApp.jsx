import React, { useEffect } from "react";
import { UserNavbar } from "../../components/UI/UserNavbar";
import { useAccount } from "../../context/ContextProvider";
import { Card } from "../../components/User/Card";
import { getUser } from "../../service/userService";

export const UserApp = () => {
  const { account, setAccount } = useAccount();

  //==================== Use Effect ====================//
  useEffect(() => {
    const getUserDetails = async () => {
      const id = localStorage.getItem("userId");
      const res = await getUser(id);
      setAccount(res.data);
    };
    getUserDetails();
  }, []);

  console.log(account);
  return (
    <div className="flex h-screen w-full flex-col items-start">
      <div>
        <UserNavbar account={account} />
      </div>
      <div className="flex flex-row ">
        <div className="flex flex-row gap-4 px-4 mt-8">
          <div>
            <Card
              name="Unauthorized Parking"
              button={"report"}
              description={
                "Can Report of unauthorized vehicle parked alongside road or private property and can ask for field visit"
              }
            />
          </div>
          <div>
            <Card
              name="Abandoned Vehicle"
              button={"report"}
              description={
                "Can Report of unauthorized vehicle parked alongside road or property more then 15 days"
              }
            />
          </div>
          <div></div>
          <div></div>
        </div>
      </div>
    </div>
  );
};
