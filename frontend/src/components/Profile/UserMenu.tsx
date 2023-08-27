import { useCurrentUser } from "@/hooks/useUser";
import {
  IconButton,
  Menu,
  MenuButton,
  MenuDivider,
  MenuGroup,
  MenuItem,
  MenuList,
} from "@chakra-ui/react";
import { FunctionComponent } from "react";
import LogoutButton from "../LogoutButton";
import UserIcon from "./UserIcon";

interface UserMenuProps {}

const UserMenu: FunctionComponent<UserMenuProps> = () => {
  const { user } = useCurrentUser();

  return (
    <Menu>
      <MenuButton
        width="40px"
        height="40px"
        ml={2}
        borderRadius={"50%"}
        as={IconButton}
      >
        <UserIcon />
      </MenuButton>
      <MenuList>
        <MenuGroup title="Your Stuff"></MenuGroup>
        <MenuDivider />
        <MenuItem>{user?.username}'s Profile</MenuItem>
        <LogoutButton />
      </MenuList>
    </Menu>
  );
};

export default UserMenu;
