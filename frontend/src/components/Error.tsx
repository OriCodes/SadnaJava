import { Alert, AlertDescription, AlertIcon } from "@chakra-ui/react";
import { FunctionComponent } from "react";

interface ErrorProps {
  error: unknown;
}

const Error: FunctionComponent<ErrorProps> = ({ error }) => {
  const message = (error as { message: string })?.message;

  return (
    <Alert
      status="error"
      variant="subtle"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      textAlign="center"
      height="200px"
    >
      <AlertIcon boxSize="40px" mr={0} />
      <AlertDescription mt={4}>
        {message || "There was an Error."}
      </AlertDescription>
    </Alert>
  );
};

export default Error;
