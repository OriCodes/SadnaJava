import { routedApi } from ".";

const api = routedApi("users");

export function register() {
  return api<void>("register", {
    method: "POST",
  });
}
