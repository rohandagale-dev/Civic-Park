import { useState } from "react";
import {
  emailValidator,
  passwordValidator,
} from "../utils/validators/formValidator";
import { loginUser } from "../service/userService";
import { Input } from "./UI/Input";
import { Button } from "./UI/Button";

const LoginForm = () => {
  const [form, setForm] = useState({ email: "", password: "" });
  const [errors, setErrors] = useState({ email: "", password: "" });

  //=============== Input Handler ==============//
  const handleChange = (e) => {
    const { name, value } = e.target;

    setForm((prev) => ({ ...prev, [name]: value }));
    setErrors((prev) => ({ ...prev, [name]: "" })); // Reset errors on change
  };

  //=============== Form Submit Handler ==============//
  const handleSubmit = (e) => {
    e.preventDefault();

    let emailError = "";
    let passwordError = "";

    if (!emailValidator(form.email)) {
      emailError = "Invalid email address";
    }
    if (passwordValidator(form.password)) {
      passwordError =
        "Password must be at least 6 characters and include a number";
    }

    setErrors({ email: emailError, password: passwordError });

    if (!emailError && !passwordError) {
      loginUser(form);
    }
  };

  return (
    <form className="flex flex-col w-80 gap-4   " onSubmit={handleSubmit} noValidate>
      <Input
        label="Email"
        name="email"
        variant="outlined"
        value={form.email}
        onChange={handleChange}
        margin="normal"
        fullWidth
        error={!!errors.email}
        helperText={errors.email}
        size="small"
        placeholder="Email"
        className=""
      />
      <div>
        <Input
          label="Password"
          name="password"
          type="password"
          variant="outlined"
          value={form.password}
          onChange={handleChange}
          margin="normal"
          fullWidth
          error={!!errors.password}
          helperText={errors.password}
          size="small"
          placeholder="••••••••"
        />
        <p className="text-xs text-right text-gray-500 hover:text-gray-900 pt-2">
          <a href="/#">Forgot password?</a>
        </p>
      </div>
      <div className="mt-4">
        <Button name="Login" />
      </div>
    </form>
  );
};

export default LoginForm;
