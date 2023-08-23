import { Box, Button, Heading, Text } from "@chakra-ui/react";
import { useNavigate, useRouteError } from "react-router-dom";

const ErrorPage = () => {
  const error = useRouteError() as { message: string };
  const navigate = useNavigate();
  return (
    <Box
      minHeight="100vh"
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      bgGradient="linear(to bottom, teal.500, teal.800)"
    >
      <Heading size="xl" color="white">
        404 - Oops, something went wrong!
      </Heading>
      <Text color="white" mt={4} textAlign="center">
        {error.message}
      </Text>
      <Button
        mt={6}
        colorScheme="teal"
        onClick={() => navigate("/", { replace: true })}
      >
        Back to Home
      </Button>
    </Box>
  );
};

export default ErrorPage;
