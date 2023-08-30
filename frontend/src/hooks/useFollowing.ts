import { getFollowers, getFollowings, isFollowing } from "@/api/follow";
import { useQuery } from "@tanstack/react-query";

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
    isFollowing: query.data,
  };
};

export const useFollowing = (userId: number) => {
  const query = useQuery(["followings", userId], () => getFollowings(userId));

  return {
    ...query,
    isFollowing: query.data,
  };
};
