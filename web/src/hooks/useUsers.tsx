import { useState, useEffect } from "react";
import { ApiPath } from "../constants/ApiPath";
import { api } from "../services/api";
import { User } from "../interfaces/User";

export const useUsers = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        setLoading(true);
        const { data } = await api.get<User[]>(ApiPath.users);
        if (!data) {
          throw new Error("Failed to fetch users");
        }

        setUsers(data);
        setError(null);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchUsers();
  }, []);

  return { users, loading, error };
};
