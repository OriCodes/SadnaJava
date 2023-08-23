import axios, { AxiosRequestConfig } from "axios";

export async function api<T>(
  endpoint: string,
  options?: AxiosRequestConfig
): Promise<T> {
  try {
    const response = await axiosInstance(`${endpoint}`, options);
    return response.data as T;
  } catch (error) {
    if (axios.isAxiosError(error)) {
      if (error.response?.status === 401) {
        throw new Error("Unauthorized.");
      }

      if (error.response?.status === 403) {
        throw new Error("Forbidden.");
      }
      const message = error.response?.data?.message || "An error occurred.";
      throw new Error(message);
    }
    throw error;
  }
}

export function routedApi(route: string) {
  return async <R>(
    endpoint: string,
    options?: AxiosRequestConfig
  ): Promise<R> => {
    return api<R>(`${route}/${endpoint}`, options);
  };
}

export const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api",
});
