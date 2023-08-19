import react from "@vitejs/plugin-react-swc";
import path from "path";
import { defineConfig, loadEnv } from "vite";

// https://vitejs.dev/config/
export default defineConfig(() => {
  const authEnv = loadEnv("auth", process.cwd());

  return {
    plugins: [react()],
    define: {
      authConfig: authEnv,
    },
    resolve: {
      alias: {
        "@": path.resolve(__dirname, "./src/"),
      },
    },
  };
});
