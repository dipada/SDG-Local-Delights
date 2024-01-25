<template>
  <div class="flex justify-center items-center h-screen flex-grow">
    <div v-if="isLoading" class="spinner-border animate-spin inline-block w-80 h-80 border-8 rounded-full" role="status">
      <span class="visually-hidden"></span>
    </div>
  </div>
</template>

<script>
import {mapActions} from 'vuex';
import axios from "axios";

export default {
  data() {
    return {
      isLoading: true,
    };
  },
  methods: {
    ...mapActions(['saveUserInfo']), // Aggiunge saveUserInfo dallo store Vuex
  },
  async created() {
    const token = this.$route.query.token;
    if (token) {
      this.saveUserInfo(token); // Salva il token e le informazioni dell'utente nello store Vuex

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
