import profile from "../../../public/profile.jpeg"

export const ProfileImage = () => {
  return (
    <img
      src={profile} 
      alt="Profile"
      className="w-10 h-10 rounded-full hover:border-4 border-gray-300 shadow-lg border-2 object-cover cursor-pointer transition-all"
    />
  );
};
