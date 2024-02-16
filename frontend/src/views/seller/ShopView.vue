<script setup>

import HeaderBase from "@/components/HeaderBase.vue";
import ShopTopContentComponentEdit from "@/components/ShopTopContentComponentEdit.vue";
import ProductContentEdit from "@/components/ProductContentEdit.vue";

</script>

<template>
  <HeaderBase/>
  <ShopTopContentComponentEdit :shop-info="shopInfo"/>
  <div class="mx-auto max-w-screen-lg">
    <main class="grid grid-cols-2 gap-x-6 gap-y-10 px-2 pb-20 sm:grid-cols-3 sm:px-8 lg:mt-16 lg:grid-cols-4 lg:gap-x-4 lg:px-0">
      <article class="relative" v-for="prodotto in prodotti">
        <div class="aspect-square overflow-hidden">
          <img class="h-full w-full object-cover transition-all duration-300 group-hover:scale-125" src="https://media.allure.com/photos/595d3d701533d77186041858/master/pass/edsfaves-07032017-lede.jpg?mbid=social_retweet" alt=""/>
        </div>
        <div class="mt-4 flex items-start justify-between">
          <div class="">
            <h3 class="text-xs font-semibold sm:text-sm md:text-base ">
              <a href="#" title="" class="">
                {{prodotto.nome}}
                <span class="absolute" aria-hidden="true"></span>
              </a>
            </h3>
          </div>
          <div class="text-right">
            <p class="text-xs font-normal sm:text-sm md:text-base">{{prodotto.prezzoAttuale}}</p>
          </div>
        </div>
      </article>

    </main>
  </div>


</template>

<script>
import ShopTopContentComponent from "@/components/ShopTopContentComponent.vue";
import axios from "axios";
import store from "@/store/index.js";

export default {
  components: {ShopTopContentComponent},
  data() {
    return {
      shopInfo: null,
      prodotti: [{
        id: 1,
        nome: "Arabian Musk",
        imageUrl: "https://media.allure.com/photos/595d3d701533d77186041858/master/pass/edsfaves-07032017-lede.jpg?mbid=social_retweet",
        prezzoAttuale: "$49.00",
        prezzoVecchio: "$79.00",
        sconto: true
      },
        {
          id: 2,
          nome: "Arabian Musk",
          imageUrl: "https://media.allure.com/photos/595d3d701533d77186041858/master/pass/edsfaves-07032017-lede.jpg?mbid=social_retweet",
          prezzoAttuale: "$49.00",
          prezzoVecchio: "$79.00",
          sconto: true
        }
        ]
    }
  },
  methods: {
    // Esempio di metodo
    getShopInfo() {
      // Chiamata API per ottenere le informazioni del negozio
      axios.get('http://localhost:8085/shop/seller/' + store.state.userInfo.email, {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*'
        },
      }).then(response => {
            console.log(response.data);
            this.shopInfo = response.data[0];
            return this.shopInfo;

          })
          .catch(error => {
            console.error(error);
          });
    }
  },
  mounted() {
    this.getShopInfo();
  }
}
</script>

<style scoped>

</style>