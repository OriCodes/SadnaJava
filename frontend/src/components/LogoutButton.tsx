import useAuthStore from "@/store/auth";
import { ExternalLinkIcon } from "@chakra-ui/icons";
import { MenuItem } from "@chakra-ui/react";
import { useQueryClient } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

interface LogoutButtonProps {}

const LogoutButton: FunctionComponent<LogoutButtonProps> = () => {
  const { logout } = useAuthStore();
  const queryClient = useQueryClient();

  const handleLogout = () => {
    logout();
    localStorage.setItem("token", "");
    queryClient.invalidateQueries();
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
