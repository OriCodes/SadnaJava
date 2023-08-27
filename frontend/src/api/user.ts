import User from "@/interfaces/user";
import UserProfile from "@/interfaces/userProfile";
import { routedApi } from ".";

const api = routedApi("users");

export function currentUser() {
  return api<User>("currentUser");
}

export function getUserProfile(userId: number, page: number = 1) {
  return api<UserProfile>(`getUserProfile/${userId}?page=${page}`);
}
