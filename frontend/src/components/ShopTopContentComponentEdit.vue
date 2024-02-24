<template>
  <div class="justify-center flex-1 max-w-6xl py-4 mx-auto lg:py-6 md:px-6">
    <div class="flex flex-wrap ">
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0">
        <img v-if="shopInfo" :src="shopInfo.imageUrl" alt="Immagine del Negozio"
             class="relative z-40 object-cover w-full h-96 rounded-3xl">
        <label class="block mb-2 text-sm font-semibold text-secondary dark:text-gray-300" for="large_size">Inserisci
          l'immagine del negozio</label>
        <input
            class="block w-full text-lg text-gray-900 border border-gray-300 rounded-lg cursor-pointer bg-gray-50 dark:text-gray-400 focus:outline-none dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400"
            id="large_size" type="file" @change="editShopImage">
      </div>
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0 ">
        <h2 v-if="shopInfo" class="mb-4 text-4xl font-semibold text-secondary dark:text-gray-300">
          Shop Name: {{ shopInfo.name }}
        </h2>
        <label for="helper-text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Change Shop
          Name</label>
        <input type="text" id="helper-text" aria-describedby="helper-text-explanation"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="new name" @change="editShopName">
        <h2 v-if="shopInfo" class="mb-4 font-semibold text-secondary dark:text-gray-300">
          description: {{ shopInfo.description }}
        </h2>
        <label for="helper-text" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Change Shop
          Description</label>
        <input type="text" id="helper-text" aria-describedby="helper-text-explanation"
               class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
               placeholder="new name" @change="editShopDescription">
        <div class="w-full mb-10 lg:mb-0 ">
          <ul class="mb-10 text-base leading-7 text-gray-500 dark:text-gray-400">
            <li v-if="shopInfo"><b>Indirizzo:</b> {{ shopInfo.address }}</li>

            <label for="address" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Edit
              address</label>
            <!--
            <input type="text" id="address"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                   placeholder="Via Roma 1" required @change="editShopAddress"/>
            -->
            <input class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" type="text" v-model="query"
                   @input="cercaIndirizzo"
                   placeholder="Inserisci un indirizzo..." required />
            <ul class="bg-gray-400" v-if="indirizzi.length">
              <li v-for="(indirizzo, index) in indirizzi" :key="index" @click="selezionaIndirizzo(indirizzo)">
                {{ indirizzo.display_name }}
              </li>
            </ul>
            <li v-if="shopInfo"><b>Telefono:</b> {{ shopInfo.phoneNumber }}</li>
            <label for="phone" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Edit phone
              number</label>
            <input type="tel" id="phone"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                   placeholder="123-45-678" pattern="[0-9]{3}-[0-9]{2}-[0-9]{3}" required @change="editShopNumber"/>
            <li v-if="shopInfo"><b>Email:</b> {{ shopInfo.shopEmail }}</li>
            <label for="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Edit email
              address</label>
            <input type="email" id="email"
                   class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                   placeholder="john.doe@shop.com" required @change="editShopEmail"/>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import {mapActions} from "vuex";
import store from "@/store/index.js";

export default {
  name: 'ShopTopContentComponent',
  props: {
    shopInfo: {
      id: Number,
      name: String,
      description: String,
      address: String,
      phoneNumber: String,
      shopEmail: String,
      imageUrl: String,
      sellerEmail: String,
      longitude: Number,
      latitude: Number
    },

  },

  data() {
    return {
      query: '',
      indirizzi: [],
      savedAddresses: [],
      selectedAddress: null,
    }
  },

  created() {

  },

  methods: {
    cercaIndirizzo() {
      if (this.query.length > 2) {
        const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.query)}`;
        axios.get(url)
            .then(response => {
              this.indirizzi = response.data;
              console.log("Indirizzi salvati: ",this.indirizzi);
            })
            .catch(error => console.error('Errore nella ricerca degli indirizzi:', error));
      }
    },
    selezionaIndirizzo(indirizzo) {
      this.query = indirizzo.display_name;
      this.indirizzi = [];
      this.selectedAddress = indirizzo; // Memorizza l'indirizzo selezionato
      if (this.selectedAddress) {
        this.savedAddresses=this.selectedAddress; // Aggiungi l'indirizzo alla lista degli indirizzi salvati
        this.selectedAddress = null; // Resetta l'indirizzo selezionato
        this.query = ''; // Pulisci la query
        this.editShopAddress(indirizzo);
      }
    },

    editShopImage(e) {
      const file = e.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        this.shopInfo.imageUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    },

    editShopName(e) {
      this.shopInfo.name = e.target.value;
    },

    editShopDescription(e) {
      this.shopInfo.description = e.target.value;
    },

    editShopAddress(e) {
      this.shopInfo.address = e.display_name;
      this.shopInfo.longitude = e.lon;
      this.shopInfo.latitude = e.lat;
    },

    editShopNumber(e) {
      this.shopInfo.phoneNumber = e.target.value;
    },

    editShopEmail(e) {
      this.shopInfo.shopEmail = e.target.value;
    },


    updateShopInfo() {
      console.log('shopInfo è cambiato', this.shopInfo);
      axios.put("http://localhost:8085/shop/update/" + this.shopInfo.id, this.shopInfo,{
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      })

          .then(response => {
            console.log(response.data);
          })
          .catch(error => {
            console.log(error);
          });
    },
  },

  watch: {
    shopInfo: {
      deep: true,
      handler(newVal, oldVal) {
        console.log('shopInfo è cambiato', newVal);
        this.updateShopInfo();
      },
    }
  }
}
</script>
