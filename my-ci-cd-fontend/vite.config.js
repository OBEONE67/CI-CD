import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'


// https://vite.dev/config/
export default defineConfig({
  test: {
    globals: true,
    environment: 'jsdom',
    // --- เพิ่มส่วนนี้เข้าไปค่ะ ---
    coverage: {
      provider: 'v8',
      reporter: ['text', 'json', 'html'],
    },
    // ---------------------------
  },
  plugins: [
    vue(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
