import { create } from "zustand";

interface AuthStore {
  token: string | null;
  isLoggedIn: boolean;
  login: (token: string) => void;
  logout: () => void;
}

const useAuthStore = create<AuthStore>()(
  (set) =>
    ({
      token: localStorage.getItem("authToken") || null,
      isLoggedIn: false,
      login: (token: string) => set({ token, isLoggedIn: true }),
      logout: () => set({ token: null, isLoggedIn: false }),
    } as const)
);

export default useAuthStore;
