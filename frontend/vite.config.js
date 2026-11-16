import { defineConfig, loadEnv } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'node:path';

// In development every request to /api is proxied to the Spring Boot backend,
// so the browser talks to a single origin and no CORS setup is needed locally.
// The backend port is read from the root .env (SERVER_PORT) - one place to change it.
export default defineConfig(({ mode }) => {
  const rootEnv = loadEnv(mode, path.resolve(process.cwd(), '..'), '');
  const env = loadEnv(mode, process.cwd(), '');
  const backend = env.VITE_API_PROXY_TARGET || `http://localhost:${rootEnv.SERVER_PORT || 8080}`;

  return {
    plugins: [react()],
    server: {
      port: 5173,
      proxy: {
        '/api': { target: backend, changeOrigin: true },
      },
    },
  };
});
