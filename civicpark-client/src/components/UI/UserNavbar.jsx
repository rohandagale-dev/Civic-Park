import { ProfileImage } from "../shared-components/Profile";
import { useAccount } from "../../context/ContextProvider";
import DropDown from "../User/DropDown";

export const UserNavbar = ({account}) => {


  console.log("what is in the account", account);
  return (
    <div className="h-16 w-full flex flex-row justify-center items-center px-4">
      <div>
        <DropDown />
      </div>
      <div>{account?.firstName}{" "}{account?.lastName}</div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  );
};
