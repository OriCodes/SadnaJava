import useAuthStore from "@/store/auth";
import { Box, Button, Flex, Heading } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Link, Outlet } from "react-router-dom";
import UserIcon from "./UserIcon";

interface PageLayoutProps {}

const PageLayout: FunctionComponent<PageLayoutProps> = () => {
  const { isLoggedIn, logout } = useAuthStore();

  const handleLogout = () => {
    logout();
    localStorage.setItem("token", "");
  };

  return (
    <Box p={4}>
      <Flex justifyContent="space-between" alignItems="center">
        <Link to="/">
          <Heading as="h1" size="lg">
            <img src="/assets/svg/TALKSphere.svg" />
          </Heading>
        </Link>
        <Flex>
          {isLoggedIn ? (
            <>
              <Button
                onClick={handleLogout}
                colorScheme="teal"
                as={Link}
                to="/"
              >
                Logout
              </Button>
              <UserIcon />
            </>
          ) : (
            <>
              <Button colorScheme="teal" as={Link} to="/login">
                Login
              </Button>
              <Button
                colorScheme="teal"
                as={Link}
                to="/register"
                marginLeft={2}
              >
                Register
              </Button>
            </>
          )}
        </Flex>
      </Flex>
      <Box>
        <Outlet />
      </Box>
    </Box>
  );
};

export default PageLayout;
