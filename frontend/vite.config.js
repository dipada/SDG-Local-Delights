import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  server:{
    port: 5173,
    strictPort: true,
    host: true,
    //origin: "http://0.0.0.0:5173",
    proxy:{
      '/apigateway/*': {
        target: 'http://localhost:30085',
        changeOrigin: true,
        secure: false,
        rewrite: (path) => path.replace(/^\/apigateway/, '')
      },

      '/auth/*':{
        target:'http://localhost:30080',
        changeOrigin:true,
        secure:false,
      },
    },
    watch:{
      usePolling:true
    },
    preview:{
      port:5173,
      strictPort: true,
    },
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})
