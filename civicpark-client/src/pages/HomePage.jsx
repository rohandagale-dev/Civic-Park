import maps from "../../public/maps.png";
import { Button } from "../components/UI/Button";

export const HomePage = () => {
  return (
    <div className="flex flex-col w-full items-center justify-center h-screen gap-12">
      <div className="">
        <p className="text-4xl text-primary font-bold">Welcome To Civic Park</p>
      </div>
      <div className="w-[60%] text-center text-gray-500">
        <p>
          Notify is a real-time chat application that seamlessly connects you
          with others. Built with Node.js and React.js, Notify offers instant
          messaging, secure interactions, and a user-friendly interface designed
          to make communication easy and enjoyable.
        </p>
      </div>
      <div className="flex flex-row gap-4">
        <div>
          <a href="/login">
            <Button name="Login">Login</Button>
          </a>
        </div>
        <div>
          <a href="/signup">
            <Button name="Register" >Sign Up</Button>
          </a>
        </div>
      </div>
      <div>
        <p className="text-xs text-gray-500 hover:text-gray-900">
          <a href="">Explore our github repository and contribute...!</a>
        </p>
      </div>
    </div>
  );
};
