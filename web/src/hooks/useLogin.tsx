import { useState } from "react";
import { api } from "../services/api";
import { ApiPath } from "../constants/ApiPath";
import { useAuthStore } from "../stores/auth-store-provider";
import { Profile } from "../interfaces/User";

export const useLogin = () => {
  const { setProfile } = useAuthStore((state) => state);

  const [loading, setLoading] = useState(false);
  const [loginError, setLoginError] = useState<{
    isOpen: boolean;
    message: string;
  }>({
    isOpen: false,
    message: "Login Failed",
  });
  const login = async (data: Record<string, any>) => {
    setLoading(true);

    try {
      const response = await api.post(ApiPath.login, data);

      const { accessToken } = response.data;
      sessionStorage.setItem("accessToken", accessToken);

      const profileResponse = await api.get<Profile>(ApiPath.me);
      setProfile(profileResponse.data);

      return { success: true };
    } catch (error: any) {
      setLoginError({
        isOpen: true,
        message: error.response?.data?.message || loginError.message,
      });
      return { success: false };
    } finally {
      setLoading(false);
    }
  };

  return { login, loading, loginError };
};
