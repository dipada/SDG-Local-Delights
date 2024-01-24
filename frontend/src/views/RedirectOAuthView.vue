<template>
  <div class="flex justify-center items-center h-screen flex-grow">
    <div v-if="isLoading" class="spinner-border animate-spin inline-block w-80 h-80 border-8 rounded-full" role="status">
      <span class="visually-hidden"></span>
    </div>
  </div>
</template>

<script>
import { jwtDecode } from 'jwt-decode';
import axios from 'axios';

export default {
  data() {
    return {
      isLoading: true,
      user: null
    };
  },
  async created() {
    const token = this.$route.query.token;
    if (token) {
      localStorage.setItem('userToken', token);
      try {
        this.user = jwtDecode(token);
        console.log('Decoded JWT:', this.user);

        // Imposta l'interceptor per aggiungere il token alle richieste future
        axios.interceptors.request.use(config => {
          config.headers.Authorization = `Bearer ${token}`;
          return config;
        });

        // Attesa di 5 secondi prima del reindirizzamento
        setTimeout(() => {
          this.isLoading = false;
          this.$router.push('/client/home');
        }, 5000);
      } catch (e) {
        console.error('Error decoding token:', e);
        this.isLoading = false;
        // Gestire l'errore di decodifica del token
      }
    } else {
      console.log('No token found');
      this.isLoading = false;
      // Gestire il caso in cui il token non sia disponibile
    }
  },
};
</script>

<style>
.spinner-border {
  border-color: transparent;
  border-top-color: #588157; /* Usa il colore che preferisci */
}
</style>
