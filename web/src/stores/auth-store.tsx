import { create } from "zustand";
import { Profile } from "../interfaces/User";

export type AuthState = {
  isLoggedIn: boolean;
  profile: Profile | null;
  isAdmin: boolean;
};

export type AuthActions = {
  setProfile: (data: Profile) => void;
  logout: () => void;
};

export type AuthStore = AuthState & AuthActions;

function getProfile(): Profile {
  const storedProfile = sessionStorage.getItem("profile");
  return storedProfile ? JSON.parse(storedProfile) : null;
}

const getIsAdmin = () => {
  return getProfile().roles.includes("ADMIN");
};

export const defaultInitState: AuthState = {
  isLoggedIn: Boolean(sessionStorage.getItem("accessToken")),
  profile: Boolean(sessionStorage.getItem("accessToken")) ? getProfile() : null,
  isAdmin: Boolean(sessionStorage.getItem("accessToken"))
    ? getIsAdmin()
    : false,
};

export const createAuthStore = (initState: AuthState = defaultInitState) => {
  return create<AuthStore>()((set) => ({
    ...initState,

    setProfile: (data: Profile) => {
      set(() => {
        sessionStorage.setItem("profile", JSON.stringify(data));

        return {
          isLoggedIn: true,
          profile: data,
          isAdmin: data.roles.includes("ADMIN"),
        };
      });
    },

    logout: () => {
      sessionStorage.removeItem("accessToken");
      sessionStorage.removeItem("profile");

      set(() => {
        return { isLoggedIn: false, profile: null, isAdmin: false };
      });
    },
  }));
};
