import { ErrorLabel } from "../shared-components/ErrorLabel";

export const Input = ({
  type,
  name,
  id,
  value,
  placeholder,
  onChange,
  label,
}) => {
  return (
    <div>
      <p className="py-1 text-sm">{label}</p>
      <input
        type={type}
        name={name}
        id={id}
        value={value}
        placeholder={placeholder}
        onChange={onChange}
        className="my-0 p-2 pl-4 rounded-lg ring-1  ring-gray-400 focus:outline-none  focus:ring-[2px] focus:ring-[#673AB7] w-full"
      />
    </div>
  );
};
