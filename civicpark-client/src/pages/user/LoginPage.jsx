import { useCallback, useEffect } from "react";
import LoginForm from "../../components/LoginForm";
import { verifyUser } from "../../service/userService";

const LoginPage = () => {
  // const getUserData = useCallback(async () => {
  //   try {
  //     const response = await verifyUser();
  //     console.log(response);
  //     if (response.status === 200) {
  //       navigate("/rto-user");
  //     }
  //   } catch (error) {
  //     console.log("You are not authorized user");
  //   }
  // }, []);

  // //==================== Use Effect ====================//
  // useEffect(() => {
  //   const getData = async () => {
  //     await getUserData();
  //   };
  //   getData();
  // }, []);

  //==================== UI ====================//
  return (
    <div className="h-screen w-full flex items-center justify-center flex-col gap-8">
      <div>
        <p className="text-2xl font-bold">Welcome Back to Civic Park</p>
      </div>
      <div>
        <LoginForm />
      </div>
    </div>
  );
};

export default LoginPage;
