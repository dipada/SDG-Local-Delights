<template>
  <div>
    <h1 class="text-black">Negozio ID: {{ shopId }}</h1>
  </div>

  <HeaderBase/>

  <div class="justify-center flex-1 max-w-6xl py-4 mx-auto lg:py-6 md:px-6">
    <div class="flex flex-wrap ">
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0">
        <img v-if="shopInfo" :src="shopInfo.imageUrl" alt="Immagine del Negozio"
             class="relative z-40 object-cover w-full h-96 rounded-3xl">
      </div>
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0 ">
        <h2 v-if="shopInfo" class="mb-4 text-4xl font-semibold text-secondary dark:text-gray-300">
          {{ shopInfo.name }}
        </h2>
        <h2 v-if="shopInfo" class="mb-4 font-semibold text-secondary dark:text-gray-300">
          {{ shopInfo.description }}
        </h2>
        <div class="w-full mb-10 lg:mb-0 ">
          <ul class="mb-10 text-base leading-7 text-gray-500 dark:text-gray-400">
            <li v-if="shopInfo"><b>Indirizzo:</b> {{ shopInfo.address }}</li>
            <li v-if="shopInfo"><b>Telefono:</b> {{ shopInfo.phoneNumber }}</li>
            <li v-if="shopInfo"><b>Email:</b> {{ shopInfo.email }}</li>
          </ul>
        </div>
      </div>
    </div>
  </div>



  <ProductCarousel/>

</template>

<script>
import {mapState} from 'vuex';
import HeaderBase from "@/components/HeaderBase.vue";
import ProductCarousel from "@/components/ProductCarousel.vue";
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";
import ShopTopContentComponent from "@/components/ShopTopContentComponent.vue";

export default {
  name: 'ClientShopView',
  components: {ShopTopContentComponent, ProductCarousel, HeaderBase},
  data() {
    return {
      shopInfo: null,
    };
  },
  computed: {
    ...mapState({
      shopId: state => state.shopId
    }),
  },
  mounted() {
    this.fetchShopInfo();
  },
  methods: {
    fetchShopInfo() {
      if (this.shopId) {
        axios.get(`http://localhost:8085/shop/get/${this.shopId}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*'
          },
        })
            .then(response => {
              this.shopInfo = response.data;
            })
            .catch(error => {
              console.log(error);
            });
      } else {
        router.push({name: 'client-home'});
      }
    }
  }
};
</script>