import ErrorPage from "@/components/ErrorPage";
import PageLayout from "@/components/PageLayout";
import TopicsPage from "@/components/Topic/TopicsPage";
import { ChakraProvider } from "@chakra-ui/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { useEffect } from "react";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import { axiosInstance } from "./api";
import LoginPage from "./components/LoginPage";
import PostPage from "./components/Post/PostPage";
import UserProfile from "./components/Profile/UserProfile";
import RegisterForm from "./components/RegisterForm";
import TopicPosts from "./components/Topic/TopicPosts";
import useAuthStore from "./store/auth";

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <ErrorPage />,
    element: <PageLayout />,
    children: [
      {
        path: "/",
        element: <TopicsPage />,
      },
      {
        path: "/login",
        element: <LoginPage />,
      },
      {
        path: "/register",
        element: <RegisterForm />,
      },
      {
        path: "/topic/:topicId",
        element: <TopicPosts />,
      },
      {
        path: "/posts/:postId",
        element: <PostPage />,
      },
      {
        path: "/user/:userId",
        element: <UserProfile />,
      },
    ],
  },
]);

const client = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: Infinity,
      retryDelay: (attemptIndex) => Math.min(1000 * 2 ** attemptIndex, 30000),
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
  const authStore = useAuthStore();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      authStore.login(token);
    }
  }, []);

  return (
    <QueryClientProvider client={client}>
      <ChakraProvider>
        <RouterProvider router={router}></RouterProvider>
      </ChakraProvider>
    </QueryClientProvider>
  );
}

export default App;
