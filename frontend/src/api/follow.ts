import { routedApi } from ".";

const api = routedApi("follows");

export function followUser(userId: number) {
  return api(`followUser?followedId=${userId}`, {
    method: "POST",
  });
}

export function unfollowUser(userId: number) {
  return api(`unfollowUser?followedId=${userId}`, {
    method: "DELETE",
  });
}

export function getFollowers(userId: number) {
  return api(`followers/${userId}`);
}

export function getFollowings(userId: number) {
  return api(`following/${userId}`);
}

export function isFollowing(userId: number) {
  return api<boolean>(`isFollowing?followedId=${userId}`);
}
