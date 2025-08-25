import LoginForm from "../../components/user/LoginForm";

const LoginPage = () => {
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
