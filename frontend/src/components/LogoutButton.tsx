import useAuthStore from "@/store/auth";
import { ExternalLinkIcon } from "@chakra-ui/icons";
import { MenuItem } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

interface LogoutButtonProps {}

const LogoutButton: FunctionComponent<LogoutButtonProps> = () => {
  const { logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    localStorage.setItem("token", "");
  };
  return (
    <MenuItem
      onClick={handleLogout}
      color={"red"}
      as={Link}
      to="/"
      icon={<ExternalLinkIcon />}
    >
      Logout
    </MenuItem>
  );
};

export default LogoutButton;
