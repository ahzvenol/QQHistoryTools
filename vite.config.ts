import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

import { resolve } from 'path'

export default defineConfig(({ command }) => ({
  plugins: [vue()],
  resolve: {
    alias: {
      "@": resolve(__dirname, "./src")
    }
  },
  server: {
    port: 8888,
    strictPort: true
  },
  build: {
    target: 'esnext',
    sourcemap: false,
  },
  esbuild: {
    drop: command === 'build' ? ["console", "debugger"] : [],
  },
}))
