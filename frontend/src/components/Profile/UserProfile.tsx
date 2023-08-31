import { useUserProfile } from "@/hooks/useUser";
import {
  Avatar,
  Box,
  Container,
  Flex,
  Heading,
  IconButton,
  Stack,
  Text,
  Tooltip,
} from "@chakra-ui/react";
import { capitalize, toNumber } from "lodash";
import { FunctionComponent, useState } from "react";
import { BiFemale, BiLeftArrow, BiMale, BiRightArrow } from "react-icons/bi";
import { useParams } from "react-router-dom";
import Error from "../Error";
import FollowStats from "../FollowStats";
import Loader from "../Loader";
import PostList from "../Post/PostList";

const UserProfile: FunctionComponent = () => {
  const paramUserId = useParams<{ userId: string }>().userId;

  const userId = toNumber(paramUserId);

  const [page, setPage] = useState(0);

  const { userProfile, error, isError, isLoading } = useUserProfile(
    userId,
    page
  );

  if (isLoading || !userProfile) {
    return <Loader />;
  }

  if (isError) {
    return <Error error={error} />;
  }

  const { user, pagePosts } = userProfile;

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

            <FollowStats userId={userId} />
          </Heading>
        </Flex>
      </Box>

      <Stack mt="6" spacing="4">
        <Heading as="h2" size="md">
          Posts by {user.username}
        </Heading>
        <PostList posts={pagePosts} />

        <Flex justifyContent="center">
          <IconButton
            aria-label="Previous Page"
            icon={<BiLeftArrow />}
            onClick={() => setPage(Math.max(page - 1, 0))}
          />
          <Text fontSize="lg" m="2">
            Page {page + 1}
          </Text>
          <IconButton
            aria-label="Next Page"
            icon={<BiRightArrow />}
            onClick={() => setPage(page + 1)}
          />
        </Flex>
      </Stack>
    </Container>
  );
};

export default UserProfile;
