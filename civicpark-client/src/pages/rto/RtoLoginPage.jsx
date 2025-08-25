import { RtoLoginForm } from "../../components/Dashboard/RtoLoginForm";

const RtoLoginPage = () => {
  return (
    <div className="flex h-screen w-ful items-center justify-center flex-col gap-8">
      <div>
        <p className="text-2xl font-bold">Login to RTO Dashboard</p>
      </div>
      <div>
        <RtoLoginForm />
      </div>
    </div>
  );
};

export default RtoLoginPage;
