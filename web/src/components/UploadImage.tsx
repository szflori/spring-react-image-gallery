import React, { useState } from "react";
import {
  Alert,
  Box,
  Button,
  CircularProgress,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Fab,
  TextField,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useUploadImage } from "../hooks/useUploadImage";

function UploadImage() {
  const [open, setOpen] = useState(false);
  const [file, setFile] = useState<File | null>(null);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const { uploadImage, uploading, uploadError } = useUploadImage(handleClose);

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files[0]) {
      setFile(event.target.files[0]);
    }
  };

  const handleUpload = () => {
    if (!file) return;

    uploadImage(file);
  };

  if (uploading) {
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

  return (
    <>
      <Fab
        color="primary"
        aria-label="add"
        sx={{ position: "fixed", bottom: 16, right: 16 }}
        onClick={handleOpen}
      >
        <AddIcon />
      </Fab>

      <Dialog open={open} onClose={handleClose}>
        <Box component="form" onSubmit={handleUpload}>
          <DialogTitle>Upload New Image</DialogTitle>

          <DialogContent sx={{ p: 5 }}>
            {uploadError && <Alert security="error">{uploadError}</Alert>}

            <TextField
              type="file"
              onChange={handleFileChange}
              inputProps={{
                accept: "image/*",
              }}
            />
          </DialogContent>

          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>

            <Button variant="contained" type="submit">
              Submit
            </Button>
          </DialogActions>
        </Box>
      </Dialog>
    </>
  );
}

export default UploadImage;
