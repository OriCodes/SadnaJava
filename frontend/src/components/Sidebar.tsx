import { Box, Button, Spacer, VStack } from "@chakra-ui/react";
import { Link } from "react-router-dom"; // Assuming you're using react-router for routing

const Sidebar = () => {
  return (
    <Box w="250px" h="100vh" bg="gray.800" color="white" p="4">
      <VStack spacing="4" align="stretch">
        <Button
          as={Link}
          to="/"
          variant="ghost"
          colorScheme="whiteAlpha"
          size="lg"
          fontWeight="normal"
        >
          Home
        </Button>
        <Button as={Link} to="/create-post" colorScheme="teal" size="lg">
          Create New Post
        </Button>
        <Button
          as={Link}
          to="/messages"
          variant="ghost"
          colorScheme="whiteAlpha"
          size="lg"
          fontWeight="normal"
        >
          Messages
        </Button>
      </VStack>
      <Spacer />
    </Box>
  );
};

export default Sidebar;
