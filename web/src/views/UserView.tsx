import React, { useEffect, useState } from "react";
import {
  Box,
  Button,
  CircularProgress,
  TextField,
  Typography,
  Stack,
  Paper,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";
import { useNavigate, useParams } from "react-router-dom";
import { User } from "../interfaces/User";
import { useUser } from "../hooks/useUser";
import { useUpdateUser } from "../hooks/useUpdateUser";

const rolesOptions = ["USER", "ADMIN"];
const permissionsOptions = ["PNG", "JPEG", "GIF"];

const UserView: React.FC = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  if (!id) {
    navigate("/users");
  }

  const { user, loading, error } = useUser(parseInt(id!));
  const { updateUser, updating, updateError } = useUpdateUser();
  const [editMode, setEditMode] = useState(false);
  const [formData, setFormData] = useState<User | null>(null);

  useEffect(() => {
    if (user) {
      setFormData(user);
    }
  }, [user]);

  const handleInputChange = (field: keyof User, value: string | string[]) => {
    if (!formData) return;
    setFormData({
      ...formData,
      [field]: value,
    });
  };

  const handleSave = async () => {
    if (!formData || !id) return;

    const success = await updateUser(parseInt(id), formData);
    if (success) {
      setEditMode(false);
    }
  };

  if (loading || updating) {
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

  if (error || !user || updateError || !formData) {
    return (
      <Typography color="error" sx={{ textAlign: "center" }}>
        {error}
      </Typography>
    );
  }

  return (
    <Box sx={{ padding: 3 }}>
      <Typography variant="h4" sx={{ marginBottom: 2 }}>
        User Details
      </Typography>
      <Paper sx={{ padding: 3 }}>
        <Stack spacing={2}>
          <TextField
            label="Username"
            value={formData.username || ""}
            onChange={(e) => handleInputChange("username", e.target.value)}
            fullWidth
            disabled={!editMode}
          />
          <TextField
            label="Email"
            value={formData.email || ""}
            onChange={(e) => handleInputChange("email", e.target.value)}
            fullWidth
            disabled={!editMode}
          />
          <FormControl fullWidth>
            <InputLabel>Roles</InputLabel>
            <Select
              multiple
              value={formData.roles}
              onChange={(e) =>
                handleInputChange("roles", e.target.value as string[])
              }
              disabled={!editMode}
            >
              {rolesOptions.map((role) => (
                <MenuItem key={role} value={role}>
                  {role}
                </MenuItem>
              ))}
            </Select>
          </FormControl>

          <FormControl fullWidth>
            <InputLabel>Permissions</InputLabel>
            <Select
              multiple
              value={formData.permissions}
              onChange={(e) =>
                handleInputChange("permissions", e.target.value as string[])
              }
              disabled={!editMode}
            >
              {permissionsOptions.map((permission) => (
                <MenuItem key={permission} value={permission}>
                  {permission}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Stack>
        <Box
          sx={{
            display: "flex",
            justifyContent: "flex-end",
            gap: 2,
            marginTop: 3,
          }}
        >
          {editMode ? (
            <>
              <Button variant="outlined" onClick={() => setEditMode(false)}>
                Cancel
              </Button>
              <Button variant="contained" color="primary" onClick={handleSave}>
                Save
              </Button>
            </>
          ) : (
            <Button
              variant="contained"
              color="primary"
              onClick={() => setEditMode(true)}
            >
              Edit
            </Button>
          )}
        </Box>
      </Paper>
    </Box>
  );
};

export default UserView;
