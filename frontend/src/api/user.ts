import User from "@/interfaces/user";
import { routedApi } from ".";

const api = routedApi("users");

export function currentUser() {
  return api<User>("currentUser");
}
