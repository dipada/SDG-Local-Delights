<template>
  <h1>seller home</h1>
  <!-- Visualizzazione dei messaggi per l'utente -->
  <div class="bg-red-800" v-if="errorMessage">{{ errorMessage }}</div>
  <div class="bg-black" v-if="welcomeMessage">{{ welcomeMessage }}</div>
  <div class="bg-green-800"> Token che passo {{store.getters.getUserToken}} </div>
  <!-- TODO: se l'utente ancora non era un seller, aggiungerà un negozio (rimandare a pagina aggiunta) -->
</template>

<script>
import axios from "axios";
import store from "@/store/index.js";

export default {
  computed: {
    store() {
      return store
    }
  },
  data() {
    return {
      errorMessage: '',
      welcomeMessage: ''
    };
  },
  created() {
    axios.get('http://localhost:8085/api/v1/user/welcome', {
      headers: {
        'Authorization': 'Bearer ' + store.getters.getUserToken,
        'Accept': '*/*'
        // Nota: Non è necessario impostare l'header 'Host' e 'Connection' in axios, verranno gestiti automaticamente
      },
      // axios gestisce il redirect automaticamente, quindi non è necessario specificarlo
    }).then(response => {
      // Gestisci la risposta positiva
      this.welcomeMessage = response.data; // Assicurati che la risposta abbia un campo 'message'
    }).catch(error => {
      // Gestisci gli errori, ad esempio mostrando un messaggio all'utente
      if (error.response && error.response.status === 401) {
        this.errorMessage = 'Non autorizzato. Verifica il tuo login.';
      } else {
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
      }
      console.error(error); // Per il debug
    });
  }
}
</script>
