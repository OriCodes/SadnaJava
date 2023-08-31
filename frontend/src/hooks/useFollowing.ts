import {
  followUser,
  getFollowers,
  getFollowings,
  isFollowing,
  unfollowUser,
} from "@/api/follow";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

export const useFolllowingData = (userId: number) => {
  const query = useQuery(["isFollowing", userId], () => isFollowing(userId));

  return {
    ...query,
    isFollowing: query.data,
  };
};

export const useFollowers = (userId: number) => {
  const query = useQuery(["followers", userId], () => getFollowers(userId));

  return {
    ...query,
    followers: query.data,
  };
};

export const useFollowing = (userId: number) => {
  const query = useQuery(["followings", userId], () => getFollowings(userId));

  return {
    ...query,
    following: query.data,
  };
};

export const useFollow = (userId: number) => {
  const queryClient = useQueryClient();
  const followMutation = useMutation(
    ["follow", userId],
    () => followUser(userId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["followers", userId]);

        queryClient.setQueriesData(["isFollowing", userId], true);
      },
    }
  );

  const unfollowMutation = useMutation(
    ["unfollow", userId],
    () => unfollowUser(userId),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["followers", userId]);

        queryClient.setQueriesData(["isFollowing", userId], false);
      },
    }
  );

  const followingData = useFolllowingData(userId);

  return {
    follow: followMutation.mutate,
    unfollow: unfollowMutation.mutate,
    isFollowing: Boolean(followingData.data),
  };
};
