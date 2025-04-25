import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import LoginView from "../views/LoginView";
import SignUpView from "../views/SignUpView";
import GalleryView from "../views/GalleryView";
import UsersView from "../views/UsersView";
import UserView from "../views/UserView";
import MainLayout from "../layouts/MainLayout";
import { AuthStoreProvider } from "../stores/auth-store-provider";

function App() {
  return (
    <AuthStoreProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<LoginView />} />
          <Route path="/signup" element={<SignUpView />} />

          <Route path="/" element={<MainLayout />}>
            <Route path="/" element={<GalleryView />} />
            <Route path="/users" element={<UsersView />} />
            <Route path="/users/:id" element={<UserView />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthStoreProvider>
  );
}

export default App;
