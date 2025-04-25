export interface User {
  id: number;
  username: string;
  email: string;
  roles: string[];
  permissions: string[];
}

export interface Profile {
  username: string;
  email: string;
  roles: string[];
  permissions: string[];
}
