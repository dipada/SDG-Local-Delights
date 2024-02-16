<template>
  <HeaderBase />
  <h1>seller home</h1>
  <!-- Visualizzazione dei messaggi per l'utente -->
  <div class="bg-red-800" v-if="errorMessage">{{ errorMessage }}</div>
  <div class="bg-black" v-if="welcomeMessage">{{ welcomeMessage }}</div>
  <div class="bg-black" v-if="noSellerMessage">{{ noSellerMessage }}</div>
  <div v-if="noSellerMessage"><form @submit.prevent="registerSeller"
                                    class="relative border border-gray-100 space-y-3 max-w-screen-md mx-auto rounded-md bg-white p-6 shadow-xl lg:p-10">
    <h1 class="mb-6 text-xl font-semibold lg:text-2xl">Register</h1>

    <div>
      <label> Vat Number </label>
      <input v-model="vatNumber" type="text" placeholder="VAT NUMBER HERE" required
             class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
      <label> Vat Number </label>
      <input v-model="shopEmail" type="text" placeholder="Shop Email" required
             class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
    </div>

    <div>
      <button type="submit" class="mt-5 w-full rounded-md bg-secondary p-2 text-center font-semibold text-white">
        Register as seller
      </button>
    </div>
  </form></div>
  <div class="bg-green-800"> Token che passo {{store.getters.getUserToken}} </div>
  <!-- TODO: se l'utente ancora non era un seller, aggiungerà un negozio (rimandare a pagina aggiunta) -->
</template>

<script>
import axios from "axios";
import store from "@/store/index.js";
import HeaderBase from "@/components/HeaderBase.vue";
import router from "@/router/index.js";

export default {
  components: {HeaderBase},
  computed: {
    store() {
      return store
    }
  },
  data() {
    return {
      errorMessage: '',
      welcomeMessage: '',
      noSellerMessage: '',
      vatNumber: '',
      shopEmail: ''
    };
  },
  created() {
    axios.get('http://localhost:8085/api/v1/user/seller/'+store.state.userInfo.email, {
      headers: {
        'Authorization': 'Bearer ' + store.getters.getUserToken,
        'Accept': '*/*'
      },
    }).then(response => {
      // Gestisci la risposta positiva
      this.welcomeMessage = response.data; // Assicurati che la risposta abbia un campo 'message'
      router.push({name: 'seller-shop'});
    }).catch(error => {
      // Gestisci gli errori, ad esempio mostrando un messaggio all'utente
      if (error.response && error.response.status === 404) {
        this.noSellerMessage = 'Non autorizzato. Registrati come seller.';
      } else {
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
      }
      console.error(error); // Per il debug
    });
  },

  methods:{
    registerSeller() {
      axios.post('http://localhost:8085/api/v1/user/seller', {
        vatNumber: this.vatNumber,
        email: store.state.userInfo.email,
        shopEmail: this.shopEmail
      }, {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*'
        }
      }).then(response => {
        // Gestisci la risposta positiva
        this.welcomeMessage = response.data; // Assicurati che la risposta abbia un campo 'message'
        router.push({name: 'seller-shop'});
      }).catch(error => {
        // Gestisci gli errori, ad esempio mostrando un messaggio all'utente
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
        console.error(error); // Per il debug
      });

      axios.post('http://localhost:8085/shop/add',{
        "name": "",
        "description": "",
        "address": "",
        "phoneNumber": "",
        "shopEmail": this.shopEmail,
        "sellerEmail": store.state.userInfo.email,
        "longitude": null,
        "latitude": null,
        "imageUrl": null
      },{
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*'
        }
      }).then(response => {
        // Gestisci la risposta positiva
        this.welcomeMessage = response.data; // Assicurati che la risposta abbia un campo 'message'
        router.push({name: 'seller-shop'});
      }).catch(error => {
        // Gestisci gli errori, ad esempio mostrando un messaggio all'utente
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
        console.error(error); // Per il debug
      });
    },
  },
}
</script>