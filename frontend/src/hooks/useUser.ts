import { currentUser } from "@/api/user";
import useAuthStore from "@/store/auth";
import { useQuery } from "@tanstack/react-query";

const useUser = () => {
  const authStore = useAuthStore();

  const query = useQuery(["user", authStore.token], currentUser, {
    enabled: authStore.isLoggedIn,
  });

  return {
    ...query,
    user: query.data,
    loggedIn: authStore.isLoggedIn,
  };
};

export default useUser;
