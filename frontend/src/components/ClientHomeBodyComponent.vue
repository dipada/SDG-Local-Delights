<template>
  <div class="flex flex-col items-center justify-center">
    <h1 class="mb-4 text-4xl font-extrabold leading-none tracking-tight text-secondary md:text-5xl lg:text-6xl dark:text-secondary">Within a few clicks, find local products near you</h1>
    <div class="border border-secondary rounded-lg p-4 max-w-md w-full">
      <form @submit.prevent="showOnMap" class="flex-col">
        <div class="mb-4">
          <div class="flex items-center mb-2">
            <label for="delivery" class="text-sm font-medium text-gray-900 dark:text-white mr-2 flex items-center">
              <svg class="mr-2" fill="#000000" height="20" width="20" version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 491.351 491.351" xml:space="preserve">
                <path id="XMLID_188_" d="M381.559,182.007c-0.895,0-1.755,0.115-2.632,0.138l-14.934-87.322c-0.809-4.710-3.613-8.842-7.674-11.355
                c-4.051-2.511-8.998-3.154-13.582-1.772l-44.975,13.622c-8.792,2.667-13.763,11.963-11.097,20.771
                c2.667,8.801,11.992,13.789,20.757,11.111l26.901-8.15l6.167,35.949H198.895v-7.076l17.403-4.393
                c6.916-1.741,11.845-8.190,11.433-15.575c-0.464-8.369-7.639-14.770-16.018-14.290l-64.285,3.653
                c-1.282,0.080-2.701,0.292-4.001,0.616c-11.415,2.889-18.349,14.487-15.467,25.906c2.882,11.419,14.495,18.339,25.911,15.458l11.707-2.959v10.815l-13.694,23.586
                c-12.964-5.610-27.142-8.733-42.032-8.733C49.282,182.007,0,233.227,0,296.184C0,359.151,49.282,410.370,109.852,410.370
                c55.081,0,100.708-42.453,108.494-97.529h17.763c4.732,0,9.239-2.017,12.396-5.547l24.432-27.287
                c-0.732,5.304-1.239,10.673-1.239,16.177c0,62.968,49.291,114.187,109.861,114.187c60.543,0,109.792-51.219,109.792-114.187
                C491.351,233.227,442.102,182.007,381.559,182.007z M109.852,377.058c-42.203,0-76.544-36.280-76.544-80.874
                c0-44.591,34.341-80.864,76.544-80.864c8.783,0,17.084,1.895,24.956,4.789l-39.365,67.709c-2.994,5.148-3.011,11.508-0.035,16.672
                c2.959,5.165,8.474,8.352,14.444,8.352h74.824C177.390,349.464,146.620,377.058,109.852,377.058z M184.676,279.528h-45.868
                l24.078-41.406C173.682,249.151,181.476,263.423,184.676,279.528z M228.660,279.528h-10.314
                c-4.001-28.383-17.988-53.433-38.255-71.018l11.743-20.202h118.464L228.660,279.528z M381.559,377.058
                c-42.211,0-76.544-36.280-76.544-80.874c0-33.481,19.355-62.272,46.874-74.536l13.239,77.351c1.403,8.118,8.431,13.842,16.397,13.842
                c0.945,0,1.892-0.072,2.829-0.236c9.085-1.553,15.166-10.167,13.618-19.232l-13.307-77.720c40.714,1.774,73.378,37.070,73.378,80.531
                C458.043,340.777,423.719,377.058,381.559,377.058z"/>
              </svg>
              Delivery
            </label>
            <input type="radio" id="delivery" name="deliveryOption" value="delivery" checked>
            <label for="pickup" class="text-sm font-medium text-gray-900 dark:text-white mr-2 ml-4 flex items-center">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg" class="w-4 h-4 text-gray-500 dark:text-gray-400 mr-2">
                <path d="M9 11V6C9 4.34315 10.3431 3 12 3C13.6569 3 15 4.34315 15 6V10.9673M10.4 21H13.6C15.8402 21 16.9603 21 17.816 20.564C18.5686 20.1805 19.1805 19.5686 19.564 18.816C20 17.9603 20 16.8402 20 14.6V12.2C20 11.0799 20 10.5198 19.782 10.092C19.5903 9.71569 19.2843 9.40973 18.908 9.21799C18.4802 9 17.9201 9 16.8 9H7.2C6.0799 9 5.51984 9 5.09202 9.21799C4.71569 9.40973 4.40973 9.71569 4.21799 10.092C4 10.5198 4 11.0799 4 12.2V14.6C4 16.8402 4 17.9603 4.43597 18.816C4.81947 19.5686 5.43139 20.1805 6.18404 20.564C7.03968 21 8.15979 21 10.4 21Z" stroke="#000000" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
              </svg>
              Pickup
            </label>
            <input type="radio" id="pickup" name="deliveryOption" value="pickup">
          </div>
          <hr class="border-t border-gray-300 dark:border-gray-600">
        </div>
        <label for="default-search" class="mb-2 text-sm font-medium text-gray-900 sr-only dark:text-white">Search</label>
        <div class="relative">
          <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
            <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
              <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
            </svg>
          </div>

          <div class="relative">
            <input type="search" v-model="query" @input="onInputChanged" id="default-search"
                   class="block w-full p-4 ps-10 text-sm text-gray-900 border border-gray-300 rounded-lg bg-gray-50 focus:ring-secondary focus:border-secondary dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 focus:ring-secondary focus:border-secondary"
                   placeholder="Your address" required>
            <button @click="showOnMap" type="submit"
                    class="text-white absolute right-2.5 bottom-2.5 bg-secondary hover:bg-secondary-dark focus:ring-4 focus:outline-none focus:ring-secondary-dark font-medium rounded-lg text-sm px-4 py-2">
              Search
            </button>
            <div v-if="indirizzi.length" class="relative w-full mt-1 text-secondary">
              <ul class="bg-white border border-gray-300 rounded-lg text-sm">
                <li v-for="(indirizzo, index) in indirizzi" :key="index"
                    @click="selezionaIndirizzo(indirizzo)"
                    class="p-2 hover:bg-gray-100 cursor-pointer">
                  {{ indirizzo.display_name }}
                </li>
              </ul>
            </div>
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: 'ClientHomeBodyComponent',

  data() {
    return {
      query: '',
      indirizzi: [],
      selectedAddress: null,
    }
  },

  methods: {
    onInputChanged() {
      clearTimeout(this.searchTimeout);
      this.searchTimeout = setTimeout(() => {
        this.searchAddress();
      }, 500);
    },

    selezionaIndirizzo(indirizzo) {
      this.query = indirizzo.display_name;
      this.indirizzi = [];
      this.selectedAddress = indirizzo;
    },

    searchAddress() {
      if (this.query.length > 2) {
        const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.query)}`;
        axios.get(url)
            .then(response => {
              this.indirizzi = response.data;
            })
            .catch(error => console.error('Errore nella ricerca degli indirizzi:', error));
      } else {
        this.indirizzi = [];
      }
    },

    showOnMap() {
      if (this.selectedAddress && this.selectedAddress.lat && this.selectedAddress.lon) {
        this.$store.commit('setSelectedCoordinates', {
          lat: this.selectedAddress.lat,
          lon: this.selectedAddress.lon
        });
      } else {
        alert("'Select an address from the list'");
      }
    },
  },

}
</script>

<style>
</style>
