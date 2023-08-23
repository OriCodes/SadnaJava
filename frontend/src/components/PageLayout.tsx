import { Box, Button, Flex, Heading } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Link, Outlet } from "react-router-dom";

interface PageLayoutProps {}

const PageLayout: FunctionComponent<PageLayoutProps> = () => {
  return (
    <Box p={4}>
      <Flex justifyContent="space-between" alignItems="center">
        <Link to="/">
          <Heading as="h1" size="lg">
            <img src="assets/svg/TALKSphere.svg" />
          </Heading>
        </Link>
        <Link to="/login">
          <Button>Log in</Button>
        </Link>
      </Flex>
      <Box>
        <Outlet />
      </Box>
    </Box>
  );
};

export default PageLayout;
