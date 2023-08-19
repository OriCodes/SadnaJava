import { Alert, AlertDescription, AlertIcon } from "@chakra-ui/react";
import { FunctionComponent } from "react";

interface ErrorProps {
  message?: string;
}

const Error: FunctionComponent<ErrorProps> = ({ message }) => {
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
