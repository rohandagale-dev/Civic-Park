import { useAccount } from "../../context/ContextProvider";

const Navbar = () => {
  const { account } = useAccount();
  return (
    // <div className="flex flex-row w-full h-8">
    //   <div>{account}</div>
    //   <div></div>
    //   <div></div>
    //   <div></div>
    // </div>
    <></>
  );
};

export default Navbar;
