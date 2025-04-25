import { useState } from "react";
import { api } from "../services/api";
import { ApiPath } from "../constants/ApiPath";

export const useUploadImage = (onSuccess: () => void) => {
  const [uploading, setUploading] = useState(false);
  const [uploadError, setUploadError] = useState<string | null>(null);

  const uploadImage = async (data: File) => {
    setUploading(true);
    setUploadError(null);

    try {
      await api.post(ApiPath.images, data);
      onSuccess();
    } catch (error: any) {
      setUploadError(error.response?.data?.message || "Failed to upload Image");
    } finally {
      setUploading(false);
    }
  };

  return { uploadImage, uploading, uploadError };
};
