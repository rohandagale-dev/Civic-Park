export const Button = ({ children, handleClick }) => {
  return (
    <button
      className="text-center w-full bg-primary text-secondarytext p-2 rounded-lg min-w-24"
      onClick={handleClick}
    >
      {children}
    </button>
  );
};
