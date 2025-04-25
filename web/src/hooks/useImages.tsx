import { useState, useEffect } from "react";
import { Image } from "../interfaces/Image";
import { ApiPath } from "../constants/ApiPath";
import { api } from "../services/api";

export const useImages = () => {
  const [images, setImages] = useState<Image[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchImages = async () => {
      try {
        setLoading(true);
        const { data } = await api.get<Image[]>(ApiPath.images);
        if (!data) {
          throw new Error("Failed to fetch images");
        }

        setImages(data);
        setError(null);
      } catch (err) {
        setError((err as Error).message);
      } finally {
        setLoading(false);
      }
    };

    fetchImages();
  }, []);

  return { images, loading, error };
};
