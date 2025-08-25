import MoreVertIcon from "@mui/icons-material/MoreVert";

const Navbar = ({ account }) => {

  console.log("in navbar: "+account)

  return (
    <div className="flex flex-row h-14 bg-white w-full items-center px-6 justify-between">
      <div>
        {/* <p className="text-sm">{account?.email}</p> */}
      </div>
      <div></div>
      <div></div>
      <div></div>
      <div>
       {account?.name}
      </div>
    </div>
  );
};

export default Navbar;
