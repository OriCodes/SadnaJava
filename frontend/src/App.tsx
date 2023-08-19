import ErrorPage from "@/components/ErrorPage";
import PageLayout from "@/components/PageLayout";
import TopicPage from "@/components/Topic/TopicPage";
import { ChakraProvider } from "@chakra-ui/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider, createBrowserRouter } from "react-router-dom";

function App() {
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

  return (
    <QueryClientProvider client={client}>
      <ChakraProvider>
        <RouterProvider router={router}></RouterProvider>
      </ChakraProvider>
    </QueryClientProvider>
  );
}

export default App;
