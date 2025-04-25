import { useState, useEffect } from "react";
import { ApiPath } from "../constants/ApiPath";
import { api } from "../services/api";
import { User } from "../interfaces/User";

export const useUser = (id: number) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        setLoading(true);
        const { data } = await api.get<User>(ApiPath.user(id));
        if (!data) {
          throw new Error("Failed to fetch user");
        }

        setUser(data);
        setError(null);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  return { user, loading, error };
};
