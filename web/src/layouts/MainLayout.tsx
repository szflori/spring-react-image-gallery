import React, { useEffect, useState } from "react";
import {
  AppBar,
  Toolbar,
  Typography,
  IconButton,
  Drawer,
  List,
  ListItemText,
  Box,
  Button,
  ListItemButton,
  Stack,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import { Outlet, useNavigate } from "react-router-dom";
import { useAuthStore } from "../stores/auth-store-provider";

function MainLayout() {
  const navigate = useNavigate();

  const { isLoggedIn, isAdmin, profile, logout } = useAuthStore(
    (state) => state
  );

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/login");
    }
  }, [isLoggedIn, isAdmin, navigate]);

  const [drawerOpen, setDrawerOpen] = useState(false);

  const toggleDrawer = () => {
    setDrawerOpen(!drawerOpen);
  };

  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <Box height="100%">
      <AppBar position="fixed">
        <Toolbar>
          {isAdmin && (
            <IconButton
              edge="start"
              color="inherit"
              aria-label="menu"
              onClick={toggleDrawer}
              sx={{ marginRight: 2 }}
            >
              <MenuIcon />
            </IconButton>
          )}

          <Typography variant="h6" sx={{ flexGrow: 1 }}>
            Image Browser App vir04
          </Typography>

          <Box>
            {isLoggedIn ? (
              <Stack
                direction="row"
                alignItems="center"
                justifyContent="center"
                spacing={2}
              >
                <Typography>{profile?.username}</Typography>
                <Button color="secondary" variant="contained" onClick={logout}>
                  Logout
                </Button>
              </Stack>
            ) : (
              <Button
                onClick={handleLogout}
                color="secondary"
                variant="contained"
              >
                Login
              </Button>
            )}
          </Box>
        </Toolbar>
      </AppBar>

      {isAdmin && (
        <Drawer anchor="left" open={drawerOpen} onClose={toggleDrawer}>
          <Box sx={{ width: 250 }} role="presentation" onClick={toggleDrawer}>
            <List>
              <ListItemButton onClick={() => navigate("/")}>
                <ListItemText primary="Gallery" />
              </ListItemButton>
              <ListItemButton onClick={() => navigate("/users")}>
                <ListItemText primary="Users" />
              </ListItemButton>
            </List>
          </Box>
        </Drawer>
      )}

      <Box
        component="main"
        maxWidth="xl"
        height="100%"
        sx={{ display: "flex", flexDirection: "column", mx: "auto" }}
      >
        <Toolbar />
        <Outlet />
      </Box>
    </Box>
  );
}

export default MainLayout;
