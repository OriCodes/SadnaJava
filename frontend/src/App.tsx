import ErrorPage from "@/components/ErrorPage";
import PageLayout from "@/components/PageLayout";
import TopicPage from "@/components/Topic/TopicPage";
import { Auth0Provider } from "@auth0/auth0-react";
import { ChakraProvider } from "@chakra-ui/react";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { RouterProvider, createBrowserRouter } from "react-router-dom";
import LoginPage from "./components/LoginPage";

declare const authConfig: {
  VITE_AUTH0_DOMAIN: string;
  VITE_AUTH0_CLIENT_ID: string;
};

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
        {
          path: "/login",
          element: <LoginPage />,
        }
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
    <Auth0Provider
      domain={authConfig.VITE_AUTH0_DOMAIN}
      clientId={authConfig.VITE_AUTH0_CLIENT_ID}
      authorizationParams={{
        redirect_uri: window.location.origin,
      }}
    >
      <QueryClientProvider client={client}>
        <ChakraProvider>
          <RouterProvider router={router}></RouterProvider>
        </ChakraProvider>
      </QueryClientProvider>
    </Auth0Provider>
  );
}

export default App;
