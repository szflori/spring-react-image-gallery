import React, { useState } from "react";
import {
  Box,
  Button,
  Modal,
  TextField,
  Typography,
  CircularProgress,
  Alert,
} from "@mui/material";
import { useAuthStore } from "../stores/auth-store-provider";

const modalStyle = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  boxShadow: 24,
  p: 4,
  borderRadius: 2,
};

interface ImageUploadModalProps {
  open: boolean;
  onClose: () => void;
  onSuccess: () => void;
}

const ImageUploadModal: React.FC<ImageUploadModalProps> = ({
  open,
  onClose,
  onSuccess,
}) => {
  const { isAdmin } = useAuthStore((state) => state);
  const [formData, setFormData] = useState({
    title: "",
    file: null as File | null,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  if (!isAdmin) {
    return null;
  }

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0] || null;
    setFormData({ ...formData, file });
  };

  const handleSubmit = async (event: React.FormEvent) => {
    event.preventDefault();

    if (!formData.file || !formData.title) {
      setError("Please provide both a title and an image file.");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const data = new FormData();
      data.append("title", formData.title);
      data.append("file", formData.file);

      const response = await fetch("/api/gallery/upload", {
        method: "POST",
        body: data,
      });

      if (!response.ok) {
        throw new Error("Failed to upload image.");
      }

      setFormData({ title: "", file: null });
      onSuccess(); // Notify parent component to refresh the gallery
      onClose(); // Close the modal
    } catch (err) {
      setError((err as Error).message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box sx={modalStyle}>
        <Typography variant="h6" sx={{ marginBottom: 2 }}>
          Upload Image
        </Typography>
        {error && (
          <Alert severity="error" sx={{ marginBottom: 2 }}>
            {error}
          </Alert>
        )}
        <form onSubmit={handleSubmit}>
          <TextField
            label="Image Title"
            fullWidth
            value={formData.title}
            onChange={(e) =>
              setFormData({ ...formData, title: e.target.value })
            }
            sx={{ marginBottom: 2 }}
          />
          <Button
            variant="contained"
            component="label"
            sx={{ marginBottom: 2 }}
          >
            Select Image
            <input
              type="file"
              accept="image/*"
              hidden
              onChange={handleFileChange}
            />
          </Button>
          {formData.file && (
            <Typography variant="body2" sx={{ marginBottom: 2 }}>
              Selected File: {formData.file.name}
            </Typography>
          )}
          <Box sx={{ display: "flex", justifyContent: "flex-end", gap: 2 }}>
            <Button variant="outlined" onClick={onClose} disabled={loading}>
              Cancel
            </Button>
            <Button
              type="submit"
              variant="contained"
              color="primary"
              disabled={loading}
            >
              {loading ? <CircularProgress size={20} /> : "Upload"}
            </Button>
          </Box>
        </form>
      </Box>
    </Modal>
  );
};

export default ImageUploadModal;
