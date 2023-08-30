import Follow from "@/interfaces/follow";
import { routedApi } from ".";

const api = routedApi("follows");

export function followUser(userId: number) {
  return api<Follow>(`follow?followedId=${userId}`, {
    method: "POST",
  });
}

export function unfollowUser(userId: number) {
  return api<void>(`unfollow?followedId=${userId}`, {
    method: "DELETE",
  });
}

export function getFollowers(userId: number) {
  return api<Follow[]>(`followers/${userId}`);
}

export function getFollowings(userId: number) {
  return api<Follow[]>(`following/${userId}`);
}

export function isFollowing(userId: number) {
  return api<boolean>(`isFollowing?followedId=${userId}`);
}
