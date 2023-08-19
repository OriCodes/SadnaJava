import { Spinner } from "@chakra-ui/react";
import { FunctionComponent } from "react";

interface LoaderProps {}

const Loader: FunctionComponent<LoaderProps> = () => {
  return <Spinner color="red.500" />;
};

export default Loader;
