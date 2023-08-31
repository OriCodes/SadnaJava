import { currentUser, getUserProfile } from "@/api/user";
import useAuthStore from "@/store/auth";
import { useQuery } from "@tanstack/react-query";

export const useCurrentUser = () => {
  const authStore = useAuthStore();

  const query = useQuery(["currentUser", authStore.token], currentUser, {
    enabled: authStore.isLoggedIn,
  });

  return {
    ...query,
    user: query.data,
    loggedIn: authStore.isLoggedIn,
  };
};

export const useUserProfile = (userId: number, page: number = 0) => {
  const authStore = useAuthStore();
  const query = useQuery(["userProfile", userId, page], () =>
    getUserProfile(userId, page)
  );

  return {
    ...query,
    userProfile: query.data,
    loggedIn: authStore.isLoggedIn,
  };
};
