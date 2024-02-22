<template>
  <HeaderBase/>
  <h1>Seller home</h1>
  <div class="mt-4 mb-4" v-if="noSellerMessage">
    <form @submit.prevent="registerSeller"
          class="relative border border-gray-100 space-y-3 max-w-screen-md mx-auto rounded-md bg-white p-6 shadow-xl lg:p-10">
      <h1 class="mb-6 text-xl font-semibold lg:text-2xl text-green-800">Register as a seller</h1>

      <div class="text-md font-medium text-secondary sm:mb-0 dark:text-gray-400">
        <div>
          <label> VAT: </label>
          <input v-model="vatNumber" @input="filterVATInput" type="number" placeholder="09469365184" required
                 class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        </div>

        <div>
          <label> Shop email: </label>
          <input v-model="shopEmail" type="email" placeholder="your@email.com" required
                 class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        </div>

        <div>
          <label> Shop address: </label>
          <input class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" type="text" v-model="query"
                 @input="cercaIndirizzo"
                 placeholder="Inserisci un indirizzo..."/>
          <div v-if="indirizzi.length" class="relative w-full mt-1 text-secondary">
            <ul class="bg-white border border-gray-300 rounded-lg text-sm" v-if="indirizzi.length">
              <li v-for="(indirizzo, index) in indirizzi" :key="index" @click="selezionaIndirizzo(indirizzo)"
                  class="p-2 hover:bg-gray-100 cursor-pointer">
                {{ indirizzo.display_name }}
              </li>
            </ul>
          </div>
        </div>
      </div>
      <div>
        <button type="submit" class="mt-5 w-full rounded-md bg-secondary p-2 text-center font-semibold text-white">
          Register as seller
        </button>
      </div>
    </form>
  </div>
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
      shopEmail: '',
      query: '',
      indirizzi: [],
      savedAddresses: [],
    };
  },
  created() {
    axios.get('http://localhost:8085/api/v1/user/seller/' + store.state.userInfo.email, {
      headers: {
        'Authorization': 'Bearer ' + store.getters.getUserToken,
        'Accept': '*/*'
      },
    }).then(response => {
      this.welcomeMessage = response.data;
      router.push({name: 'seller-shop'});
    }).catch(error => {
      if (error.response && error.response.status === 404) {
        this.noSellerMessage = 'Non autorizzato. Registrati come seller.';
      } else {
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
      }
      console.error(error);
    });
  },

  methods: {

    filterVATInput(event) {
      const value = event.target.value;
      this.formData.vatNumber = value.replace(/[^0-9]/g, '');
    },

    validateData() {
      // email validation
      const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
      if (!emailRegex.test(this.shopEmail)) {
        alert("Insert a valid email address");
        return false;
      }
      return true;
    },

    cercaIndirizzo() {
      clearTimeout(this.searchTimeout);
      this.searchTimeout = setTimeout(() => {
        if (this.query.length > 2) {
          const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.query)}`;
          axios.get(url)
              .then(response => {
                this.indirizzi = response.data;
                console.log("Indirizzi salvati: ", this.indirizzi);
              })
              .catch(error => console.error('Errore nella ricerca degli indirizzi:', error));
        }
      }, 500);
    },
    selezionaIndirizzo(indirizzo) {
      this.query = indirizzo.display_name;
      this.indirizzi = [];
      this.selectedAddress = indirizzo;
      if (this.selectedAddress) {
        this.savedAddresses.push(this.selectedAddress);
        this.updateMapMarkers();
        this.selectedAddress = null;
        this.query = '';
      }
    },
    saveAddress() {
      if (this.selectedAddress) {
        this.savedAddresses.push(this.selectedAddress);
        this.updateMapMarkers();
        this.selectedAddress = null;
        this.query = '';
      }
    },
    registerSeller() {
      if (!this.validateData()) {
        alert("Check the form for errors.");
        return;
      }
      console.log(JSON.stringify(this.formData));

      axios.post('http://localhost:8085/api/v1/user/seller', {
        vatNumber: this.vatNumber,
        email: this.$store.state.userInfo.email,
        shopEmail: this.shopEmail
      }, {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*'
        }
      }).then(response => {
        this.welcomeMessage = response.data;
        router.push({name: 'seller-shop'});
      }).catch(error => {
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
        console.error(error);
      });

      axios.post('http://localhost:8085/shop/add', {
        "name": "",
        "description": "",
        "address": this.savedAddresses[0].display_name,
        "phoneNumber": "",
        "shopEmail": this.shopEmail,
        "sellerEmail": store.state.userInfo.email,
        "longitude": this.savedAddresses[0].lon,
        "latitude": this.savedAddresses[0].lat,
        "imageUrl": null
      }, {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*'
        }
      }).then(response => {
        this.welcomeMessage = response.data;
        router.push({name: 'seller-shop'});
      }).catch(error => {
        this.errorMessage = 'Si è verificato un errore. Riprova più tardi.' + error;
        console.error(error);
      });
    },
  },
}
</script>