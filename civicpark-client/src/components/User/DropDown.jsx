import * as React from "react";
import Button from "@mui/material/Button";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { ProfileImage } from "../shared-components/Profile";
import { useNavigate } from "react-router-dom";
import { logOutUser } from "../../service/userService";

export default function DropDown() {
  const [anchorEl, setAnchorEl] = React.useState(null);

  const navigate = useNavigate();

  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const logout = async () => {
    const response = await logOutUser();
    if (response.status == 200) {
      navigate("/login");
    }
  };

  return (
    <div>
      <Button
        id="basic-button"
        aria-controls={open ? "basic-menu" : undefined}
        aria-haspopup="true"
        aria-expanded={open ? "true" : undefined}
        onClick={handleClick}
        variant="text"
        className="hover:animate-none hover:bg-inherit"
      >
        <ProfileImage />
      </Button>
      <Menu
        id="basic-menu"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        slotProps={{
          list: {
            "aria-labelledby": "basic-button",
          },
        }}
      >
        <MenuItem
          onClick={() => {
            navigate("/rto-user/profile");
          }}
        >
          Profile
        </MenuItem>
        <MenuItem onClick={handleClose} className="hover:bg-primary">My account</MenuItem>
        <MenuItem onClick={handleClose}>Report History</MenuItem>
        <MenuItem onClick={logout}>Logout</MenuItem>
      </Menu>
    </div>
  );
}
