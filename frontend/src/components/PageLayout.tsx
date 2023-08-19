import { useAuth0 } from "@auth0/auth0-react";
import { Box, Button, Flex, Heading } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Outlet } from "react-router-dom";

interface PageLayoutProps {}

const PageLayout: FunctionComponent<PageLayoutProps> = () => {
  const { isAuthenticated, loginWithPopup, logout } = useAuth0();
  return (
    <Box p={4}>
      <Flex justifyContent="space-between" alignItems="center">
        <Heading as="h1" size="lg">
          TALKSphere
        </Heading>
        {isAuthenticated ? (
          <Button onClick={() => logout()}>Logout</Button>
        ) : (
          <Button onClick={() => loginWithPopup()}>Login</Button>
        )}
      </Flex>
      <Box>
        <Outlet />
      </Box>
    </Box>
  );
};

export default PageLayout;
