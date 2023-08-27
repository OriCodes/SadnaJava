import { useUserProfile } from "@/hooks/useUser";
import {
  Avatar,
  Box,
  Container,
  Flex,
  Heading,
  Stack,
  Text,
} from "@chakra-ui/react";
import { toNumber } from "lodash";
import { FunctionComponent } from "react";
import { useParams } from "react-router-dom";
import Error from "../Error";
import Loader from "../Loader";
import PostList from "../Post/PostList";

const UserProfile: FunctionComponent = () => {
  const paramUserId = useParams<{ userId: string }>().userId;

  const userId = toNumber(paramUserId);

  const { userProfile, error, isError, isLoading } = useUserProfile(userId);

  if (isLoading || !userProfile) {
    return <Loader />;
  }

  if (isError) {
    return <Error error={error} />;
  }

  const { user, pagePosts, page } = userProfile;

  return (
    <Container maxW="container.md" mt="4">
      <Box p="4" borderWidth="1px" borderRadius="md" boxShadow="md">
        <Flex alignItems="center">
          <Avatar
            src={user.profileUrl}
            name={user.username}
            size="xl"
            mb="4"
            m={4}
          />
          <Heading as="h1" size="lg" mb="2">
            {user.username}
          </Heading>
        </Flex>
      </Box>

      <Stack mt="6" spacing="4">
        <Heading as="h2" size="md">
          Posts by {user.username}
        </Heading>
        <PostList posts={pagePosts} />
        <Text>{page} Page</Text>
      </Stack>
    </Container>
  );
};

export default UserProfile;
