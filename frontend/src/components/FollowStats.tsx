import { useFollow, useFollowers, useFollowing } from "@/hooks/useFollowing";
import { useCurrentUser } from "@/hooks/useUser";
import { Button, Flex, Text } from "@chakra-ui/react";
import { FunctionComponent } from "react";

interface FollowStatsProps {
  userId: number;
}

const FollowStats: FunctionComponent<FollowStatsProps> = ({ userId }) => {
  const { user } = useCurrentUser();

  const { followers } = useFollowers(userId);
  const { following } = useFollowing(userId);

  const { follow, unfollow, isFollowing } = useFollow(userId);

  return (
    <>
      <Flex>
        <Text fontSize="md" fontWeight="light">
          {followers?.length ?? "..."} Followers
        </Text>
        <Text fontSize="md" fontWeight="light" ml="4">
          {following?.length ?? "..."} Following
        </Text>
      </Flex>
      {user?.userId === userId ? (
        <></>
      ) : isFollowing ? (
        <Button onClick={() => unfollow()} colorScheme="teal" mt="4">
          Unfollow
        </Button>
      ) : (
        <Button onClick={() => follow()} colorScheme="teal" mt="4">
          Follow
        </Button>
      )}
    </>
  );
};

export default FollowStats;
