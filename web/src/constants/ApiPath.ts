export const ApiPath = {
  login: "/auth/login",
  signup: "/auth/signup",
  me: "/auth/me",
  images: "/images",
  image: (id: number) => ApiPath.images + `/${id}`,
  users: "/users",
  user: (id: number) => ApiPath.users + `/${id}`,
};
