import { useState } from "react";
import { User } from "../interfaces/User";
import { api } from "../services/api";
import { ApiPath } from "../constants/ApiPath";

export const useUpdateUser = () => {
  const [updating, setUpdating] = useState(false);
  const [updateError, setUpdateError] = useState<string | null>(null);

  const updateUser = async (id: number, userData: User): Promise<boolean> => {
    setUpdating(true);
    setUpdateError(null);

    try {
      await api.patch(ApiPath.user(id), userData);
      return true;
    } catch (error: any) {
      setUpdateError(error.response?.data?.message || "Failed to update user");
      return false;
    } finally {
      setUpdating(false);
    }
  };

  return { updateUser, updating, updateError };
};
