import { useState } from "react";
import { LoginForm } from "./components/LoginForm";

function App() {
  const [count, setCount] = useState(0);

  return (
    <div className="flex items-center justify-center w-full h-screen">
      <div>
        <div className="pb-12">
          <p className="text-3xl font-bold text-center text-primary" >
            Login to Civic Park
          </p>
        </div>
        <LoginForm />
      </div>
    </div>
  );
}

export default App;
