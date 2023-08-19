import { Box, Flex, Heading } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { Outlet } from "react-router-dom";

interface PageLayoutProps {}

const PageLayout: FunctionComponent<PageLayoutProps> = () => {
  return (
    <Box p={4}>
      <Flex justifyContent="space-between" alignItems="center">
        <Heading as="h1" size="lg">
          Forum Sadna
        </Heading>
      </Flex>
      <Box>
        <Outlet />
      </Box>
    </Box>
  );
};

export default PageLayout;
