import { fetchAllTopics } from "@/api/topic";
import Error from "@/components/Error";
import Loader from "@/components/Loader";
import useAuthStore from "@/store/auth";
import {
  Box,
  Button,
  Container,
  Flex,
  Heading,
  Image,
  Text,
} from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import { Link } from "react-router-dom";
import TopicList from "./TopicList";

interface TopicPageProps {}

const TopicsPage: FunctionComponent<TopicPageProps> = () => {
  const {
    data: topics,
    isLoading,
    isError,
    error,
  } = useQuery(["topics"], fetchAllTopics);

  const { isLoggedIn } = useAuthStore();

  if (isLoading) return <Loader />;

  if (isError || !topics) return <Error error={error} />;

  return (
    <>
      {" "}
      <Box p={20} display="flex" alignItems="center">
        <Container maxW="container.lg">
          <Box textAlign="center">
            <Heading as="h1" size="xl" mb="4">
              <Flex
                alignItems={"center"}
                justifyContent={"center"}
                flexDir={"column"}
              >
                Welcome to{" "}
                <Image
                  src="/assets/svg/TALKSphere.svg"
                  alt="logo"
                  w={"400px"}
                />{" "}
              </Flex>
            </Heading>
            <Text fontSize="lg" mb="6" color={"gray.500"}>
              Enter the Sphere of Discussions: Explore, Connect, and Share on
              TalkSphere
            </Text>
            {!isLoggedIn && (
              <Button colorScheme="blue" size="lg" as={Link} to={"/login"}>
                Login
              </Button>
            )}
          </Box>
        </Container>
      </Box>
      <TopicList topics={topics} />;
    </>
  );
};

export default TopicsPage;
