import { RegisterRequest, register } from "@/api/auth";
import useAuthStore from "@/store/auth";
import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Input,
  Select,
  Text,
} from "@chakra-ui/react";
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

interface RegisterFormProps {}

const RegisterForm: React.FC<RegisterFormProps> = () => {
  const [formData, setFormData] = useState({
    userName: "",
    password: "",
    dob: new Date(),
    gender: "MALE" as RegisterRequest["gender"],
  });

  const authStore = useAuthStore();
  const navigate = useNavigate();

  const onSubmit = async (data: RegisterRequest) => {
    const token = await register(data);

    localStorage.setItem("token", token.token);
    authStore.login(token.token);

    navigate("/");
  };

  const handleChange = <T extends keyof RegisterRequest>(
    field: T,
    value: RegisterRequest[T]
  ) => {
    setFormData((prevData) => ({ ...prevData, [field]: value }));
  };

  const handleSubmit = () => {
    const avatar = `https://api.dicebear.com/6.x/open-peeps/svg?seed=${formData.userName}`;

    const newOptions: RegisterRequest = {
      profileUrl: avatar,
      ...formData,
    };

    onSubmit(newOptions);
  };

  return (
    <Box maxW="400px" mx="auto" mt={6}>
      <FormControl>
        <FormLabel>Username</FormLabel>
        <Input
          value={formData.userName}
          onChange={(e) => handleChange("userName", e.target.value)}
        />
      </FormControl>

      <FormControl mt={4}>
        <FormLabel>Password</FormLabel>
        <Input
          type="password"
          value={formData.password}
          onChange={(e) => handleChange("password", e.target.value)}
        />
      </FormControl>

      <FormControl mt={4}>
        <FormLabel>Date of Birth</FormLabel>
        <Input
          type="date"
          onChange={(e) => handleChange("dob", new Date(e.target.value))}
        />
      </FormControl>

      <FormControl mt={4}>
        <FormLabel>Gender</FormLabel>
        <Select
          value={formData.gender}
          onChange={(e) =>
            handleChange("gender", e.target.value as RegisterRequest["gender"])
          }
        >
          <option value="MALE">Male</option>
          <option value="FEMALE">Female</option>
          <option value="OTHER">Other</option>
        </Select>
      </FormControl>

      <Button mt={6} colorScheme="blue" onClick={handleSubmit}>
        Register
      </Button>
      <Text mt={2}>
        Already have an account? <Link to="/login">Login</Link>
      </Text>
    </Box>
  );
};

export default RegisterForm;
