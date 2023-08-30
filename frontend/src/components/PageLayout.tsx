import useAuthStore from "@/store/auth";
import { Box, Button, Flex, Heading } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Link, Outlet } from "react-router-dom";
import UserMenu from "./Profile/UserMenu";
import Sidebar from "./Sidebar";

interface PageLayoutProps {}

const PageLayout: FunctionComponent<PageLayoutProps> = () => {
  const { isLoggedIn } = useAuthStore();

  return (
    <Box>
      <Flex p={2} justifyContent="space-between" alignItems="center">
        <Link to="/">
          <Heading as="h1" size="lg">
            <img src="/assets/svg/TALKSphere.svg" />
          </Heading>
        </Link>
        <Flex>
          {isLoggedIn ? (
            <>
              <UserMenu />
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
      <Flex>
        <Sidebar />
        <Box w="100%">
          <Outlet />
        </Box>
      </Flex>
    </Box>
  );
};

export default PageLayout;
