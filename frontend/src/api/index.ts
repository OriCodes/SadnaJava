const BASE_URL = "http://localhost:8080/api";

export async function api<T>(
  endpoint: string,
  options?: RequestInit
): Promise<T> {
  return fetch(`${BASE_URL}/${endpoint}`, options).then((response) => {
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    return response.json() as Promise<T>;
  });
}

export function routedApi(route: string) {
  return async <R>(endpoint: string, options?: RequestInit): Promise<R> => {
    return api<R>(`${route}/${endpoint}`, options);
  };
}
