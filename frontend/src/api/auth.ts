import { routedApi } from ".";

const api = routedApi("auth");

export interface AuthRequest {
  userName: string;
  password: string;
}

export interface RegisterRequest {
  userName: string;
  password: string;
  dob: Date;
  profileUrl: string;
  gender: "MALE" | "FEMALE" | "OTHER";
}

export interface AuthResponse {
  token: string;
}

export function authenticate(authRequest: AuthRequest) {
  return api<AuthResponse>("authenticate", {
    method: "POST",
    data: authRequest,
  });
}

export function register(data: RegisterRequest) {
  return api<AuthResponse>("register", {
    method: "POST",
    data,
  });
}
