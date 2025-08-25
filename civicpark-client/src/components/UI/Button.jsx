export const Button = ({ children, onClick }) => {
  return (
    <button
      className="text-center w-full bg-primary text-secondarytext p-2 rounded-lg min-w-24"
      onClick={onClick}
      type="button"
    >
      {children}
    </button>
  );
};
