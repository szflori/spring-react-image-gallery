import React from "react";
import {
  Box,
  Typography,
  ImageList,
  ImageListItem,
  CircularProgress,
  Stack,
  IconButton,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

import { useAuthStore } from "../stores/auth-store-provider";
import { useImages } from "../hooks/useImages";
import { useDeleteImage } from "../hooks/useDeleteImage";
import UploadImage from "../components/UploadImage";

const GalleryView: React.FC = () => {
  const { isAdmin } = useAuthStore((state) => state);
  const { images, loading, error } = useImages();
  const { deleteImage, deleting, deleteError } = useDeleteImage();

  if (loading || deleting) {
    return (
      <Box
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          height: "50vh",
        }}
      >
        <CircularProgress />
      </Box>
    );
  }

  if (error || deleteError) {
    return (
      <Typography color="error" sx={{ textAlign: "center" }}>
        {error ?? deleteError}
      </Typography>
    );
  }

  const handleDeleteImage = (id: number) => {
    if (!isAdmin) return;

    deleteImage(id);
  };

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" sx={{ marginBottom: 2, textAlign: "center" }}>
        Gallery
      </Typography>

      <ImageList variant="masonry" cols={3} gap={8}>
        {images.map((image, idx) => (
          <ImageListItem key={idx}>
            <img
              src={`data:${image.type};base64,${image.data}`}
              alt={image.name}
              loading="lazy"
              style={{ borderRadius: "8px" }}
            />

            <Stack
              direction="row"
              alignItems="center"
              justifyContent="center"
              spacing={2}
            >
              <Typography
                variant="caption"
                sx={{ textAlign: "center", display: "block" }}
              >
                {image.name}
              </Typography>

              {isAdmin && (
                <IconButton onClick={() => handleDeleteImage(image.id)}>
                  <DeleteIcon />
                </IconButton>
              )}
            </Stack>
          </ImageListItem>
        ))}
      </ImageList>

      {isAdmin && <UploadImage />}
    </Box>
  );
};

export default GalleryView;
