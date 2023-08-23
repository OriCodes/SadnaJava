import { authenticate } from "@/api/auth";
import useAuthStore from "@/store/auth";
import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Input,
  Stack,
  useToast,
} from "@chakra-ui/react";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const LoginPage: React.FC = () => {
  const [name, setName] = useState("");
  const [password, setPassword] = useState("");

  const toast = useToast();
  const navigate = useNavigate();

  const authStore = useAuthStore();

  const handleLogin = async () => {
    try {
      const { token } = await authenticate({ userName: name, password });

      localStorage.setItem("token", token);
      authStore.login(token);

      navigate("/", { replace: true });
    } catch (error) {
      console.error(error);
      const errorMessage = (error as { message: string })?.message;
      toast({
        title: errorMessage || "An error occurred.",
        status: "error",
        duration: 3000,
        isClosable: true,
      });
    }
  };

  return (
    <Box
      minH="100vh"
      display="flex"
      alignItems="center"
      justifyContent="center"
    >
      <Box
        borderWidth={1}
        px={4}
        py={8}
        rounded="lg"
        boxShadow="lg"
        maxW="md"
        w="full"
      >
        <Stack spacing={4}>
          <FormControl id="name">
            <FormLabel>Username</FormLabel>
            <Input
              type="name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </FormControl>
          <FormControl id="password">
            <FormLabel>Password</FormLabel>
            <Input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
          </FormControl>
          <Button colorScheme="orange" onClick={handleLogin}>
            Log in
          </Button>
        </Stack>
      </Box>
    </Box>
  );
};

export default LoginPage;
