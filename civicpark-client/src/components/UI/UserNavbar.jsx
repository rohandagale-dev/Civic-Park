import DropDown from "../User/DropDown";

export const UserNavbar = ({ account }) => {
  return (
    <div className="h-16 w-full flex flex-row justify-center items-center px-4">
      <div>
        <DropDown />
      </div>
      <div>
        {account?.firstName} {account?.lastName}
      </div>
      <div></div>
      <div></div>
      <div></div>
    </div>
  );
};
