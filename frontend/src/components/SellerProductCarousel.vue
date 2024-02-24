<template>

  <!-- Form per aggiungere un nuovo prodotto -->
  <div class="p-4 mx-auto max-w-7xl w-full">
    <div class="border-dashed border-2 border-green-600 rounded p-4 mb-6">
      <h2 class="text-lg font-bold mb-4 text-green-600">Aggiungi un nuovo prodotto</h2>
      <div class="flex flex-wrap -mx-2">
        <div class="px-2 w-full sm:w-1/2 md:w-1/3 lg:w-1/4">
          <!-- Sezione per il caricamento delle foto -->
          <div class="mb-4">
            <!-- ... -->
          </div>
        </div>
        <div class="px-2 w-full sm:w-1/2 md:w-2/3 lg:w-3/4">
          <form @submit.prevent="addProduct" class="flex flex-wrap">
            <div class="w-full mb-4">
              <label for="image" class="block text-sm font-medium text-green-600">Carica la foto</label>
              <input type="file" id="image" v-on:change="handleImage" required
                     class="mt-1 block w-full text-sm text-green-600 border border-green-600 rounded-lg cursor-pointer bg-gray-50">
            </div>
            <div class="w-full md:w-1/2 px-2 mb-4 text-black">
              <label for="name" class="block text-sm font-medium text-green-600">Nome Prodotto</label>
              <input type="text" id="name" v-model="newProduct.name" required
                     class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm">
            </div>
            <div class="w-full md:w-1/2 px-2 mb-4 text-black">
              <label for="description" class="block text-sm font-medium text-green-600">Breve descrizione</label>
              <textarea id="description" v-model="newProduct.description" rows="3"
                        class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm"></textarea>
            </div>
            <div class="w-full md:w-1/2 px-2 mb-4">
              <label for="category" class="block text-sm font-medium text-green-600">Categoria</label>
              <select id="category" v-model="newProduct.category" required
                      class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm text-black">
                <option value="" disabled>Scegli una categoria</option>
                <!-- Popola le categorie qui -->
                <option value="categoria1">Frutta</option>
                <option value="categoria2">Verdura</option>
                <option value="categoria3">Carne</option>
                <option value="categoria4">Pesce</option>
              </select>
            </div>
            <div class="w-full md:w-1/2 px-2 mb-4">
              <label for="brand" class="block text-sm font-medium text-green-600">Brand</label>
              <input type="text" id="brand" v-model="newProduct.brand" required
                     class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm text-black">
            </div>
            <div class="w-full md:w-1/2 px-2 mb-4">
              <label for="price" class="block text-sm font-medium text-green-600">Prezzo</label>
              <input type="number" id="price" v-model="newProduct.price" required
                     class="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm text-black">
            </div>
            <div class="w-full px-2">
              <button type="submit" class="px-4 py-2 bg-green-600 text-white rounded hover:bg-green-600">Aggiungi
                Prodotto
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
  <section class="flex dark:bg-gray-800">
    <div class="p-4 mx-auto max-w-7xl">

      <div class="grid grid-cols-1 gap-4 lg:gap-4 sm:gap-4 sm:grid-cols-2 lg:grid-cols-4">
        <div v-for="item in items" :key="item.id" class="mt-56 bg-white rounded shadow dark:bg-gray-700">
          <div class="relative z-20 p-6 group">
            <div class="relative block h-64 mb-4 -mt-56 overflow-hidden rounded -top-full ">
              <img class="pointer-events-none object-cover w-full h-full transition-all group-hover:scale-110"
                   :src="item.image" :alt="item.name">
              <div @click="deleteProduct(item.id)" class="absolute flex flex-col top-4 right-4">
                <div
                    class="active:animate-ping active:bg-green-800 relative flex items-center justify-center p-3 mb-3 transition-all translate-x-20 bg-white rounded dark:bg-gray-700 dark:text-white group-hover:translate-x-0 wishlist hover:bg-blue-200 dark:hover:bg-blue-600 group">
                  <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="black" class="bi bi-trash"
                       viewBox="0 0 16 16">
                    <path
                        d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0A.5.5 0 0 1 8.5 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
                    <path fill-rule="evenodd"
                          d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
                  </svg>
                </div>

              </div>

            </div>
            <a href="#">
              <h2 class="mb-2 text-xl font-bold text-black dark:text-white">
                {{ item.name }}
              </h2>
            </a>
            <p class="mb-3 text-lg font-bold text-blue-500 dark:text-blue-300 ">
              <span>{{ item.price }} €</span>
            </p>
          </div>
        </div>

      </div>
    </div>
  </section>
</template>

<script>
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";

export default {
  name: 'SellerProductCarousel',
  props: {
    items: Array,

  },

  data() {
    return {
      newProduct: {
        name: '',
        description: '',
        category: '',
        brand: '',
        price: '',
        image: '',
      },
    }
  },


  methods: {
    handleImage(e) {
      const file = e.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        this.newProduct.image= e.target.result;
      };
      reader.readAsDataURL(file);
    },
    // Funzione per aggiungere un nuovo prodotto
    addProduct() {
      console.log('Product added to shop:', this.newProduct);
      axios.post("http://localhost:8085/shop/addProduct/"+ this.$store.getters.getShopId , this.newProduct, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      }).then(response => {
        console.log(response.data);
        router.push({name: 'seller-home'})
      }).catch(error => {
        console.log(error);
      });
    },

    deleteProduct(productId) {
      // Conferma dell'eliminazione
      if (!confirm("Sei sicuro di voler eliminare questo prodotto?")) {
        return;
      }
      console.log('Product added to cart:', productId);
      axios.delete("http://localhost:8085/product/delete/" + productId, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      }).then(response => {
        console.log(response);
        router.push({name: 'seller-home'})
      }).catch(error => {
        console.log(error);
      });
    }
  },
};
</script>

<style scoped>

</style>
