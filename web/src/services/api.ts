import axios from "axios";
import { ApiUrl } from "../config";

const baseAxios = axios.create({
  baseURL: ApiUrl,
  timeout: 15000,
  withCredentials: true,
});

const getAuthHeaders = () => {
  const token = sessionStorage.getItem("accessToken");
  return {
    "Content-Type": "application/json",
    ...(token ? { Authorization: `Bearer ${token}` } : {}),
  };
};

export const api = {
  get: <T>(url: string, params?: object) =>
    baseAxios.get<T>(url, {
      headers: getAuthHeaders(),
      ...params,
    }),

  post: <T>(url: string, data: T, params?: object) =>
    baseAxios.post<T>(url, data, {
      headers: getAuthHeaders(),
      ...params,
    }),

  patch: <T>(url: string, data: T, params?: object) =>
    baseAxios.patch<T>(url, data, {
      headers: getAuthHeaders(),
      ...params,
    }),

  delete: <T>(url: string, params?: object) =>
    baseAxios.delete<T>(url, {
      headers: getAuthHeaders(),
      ...params,
    }),
};
