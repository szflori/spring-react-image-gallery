import { useState } from "react";
import { api } from "../services/api";
import { ApiPath } from "../constants/ApiPath";

export const useDeleteImage = () => {
  const [deleting, setDeleting] = useState(false);
  const [deleteError, setDeleteError] = useState<string | null>(null);

  const deleteImage = async (id: number): Promise<boolean> => {
    setDeleting(true);
    setDeleteError(null);

    try {
      await api.delete(ApiPath.image(id));
      return true;
    } catch (error: any) {
      setDeleteError(error.response?.data?.message || "Failed to delete user");
      return false;
    } finally {
      setDeleting(false);
    }
  };

  return { deleteImage, deleting, deleteError };
};
