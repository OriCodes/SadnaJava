import { useUserProfile } from "@/hooks/useUser";
import {
  Avatar,
  Box,
  Container,
  Flex,
  Heading,
  Stack,
  Text,
  Tooltip,
} from "@chakra-ui/react";
import { capitalize, toNumber } from "lodash";
import { FunctionComponent } from "react";
import { BiFemale, BiMale } from "react-icons/bi";
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

  const { gender } = user;

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
            <Flex alignItems="center">
              {user.username}
              <Tooltip title={capitalize(gender)}>
                {gender === "MALE" ? (
                  <BiMale />
                ) : gender === "FEMALE" ? (
                  <BiFemale />
                ) : (
                  <></>
                )}
              </Tooltip>
            </Flex>

            <Flex>
              <Text fontSize="md" fontWeight="light">
                {user.followedCount} Following
              </Text>
              <Text fontSize="md" fontWeight="light" ml="4">
                {user.followerCount} Followers
              </Text>
            </Flex>
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
