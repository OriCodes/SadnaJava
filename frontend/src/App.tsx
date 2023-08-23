import ErrorPage from "@/components/ErrorPage";
import PageLayout from "@/components/PageLayout";
import TopicPage from "@/components/Topic/TopicPage";
import { ChakraProvider } from "@chakra-ui/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { axiosInstance } from "./api";
import LoginPage from "./components/LoginPage";
import RegisterForm from "./components/RegisterForm";
import useAuthStore from "./store/auth";

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <ErrorPage />,
    element: <PageLayout />,
    children: [
      {
        path: "/",
        element: <TopicPage />,
      },
      {
        path: "/login",
        element: <LoginPage />,
      },
      {
        path: "/register",
        element: <RegisterForm />,
      },
    ],
  },
]);

const client = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: Infinity,
    },
  },
});

useAuthStore.subscribe(
  (state) =>
    (axiosInstance.defaults.headers.common["Authorization"] = state.token
      ? `Bearer ${state.token}`
      : null)
);

function App() {
  return (
    <QueryClientProvider client={client}>
      <ChakraProvider>
        <RouterProvider router={router}></RouterProvider>
      </ChakraProvider>
    </QueryClientProvider>
  );
}

export default App;
