const BASE_URL = "http://localhost:8080/api";

export async function api<T>(endpoint: string): Promise<T> {
  return fetch(`${BASE_URL}/${endpoint}`).then((response) => {
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    return response.json() as Promise<T>;
  });
}
