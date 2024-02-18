<script setup>

import HeaderBase from "@/components/HeaderBase.vue";
import ShopTopContentComponentEdit from "@/components/ShopTopContentComponentEdit.vue";
import SellerProductCarousel from "@/components/SellerProductCarousel.vue";


</script>

<template>
  <HeaderBase/>
  <ShopTopContentComponentEdit :shop-info="shopInfo"/>
  <SellerProductCarousel :items="products" :new-product/>

</template>

<script>

import {mapActions, mapState} from "vuex";
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";
import ShopTopContentComponentEdit from "@/components/ShopTopContentComponentEdit.vue";
import SellerProductCarousel from "@/components/SellerProductCarousel.vue";

export default {
  name: 'ShopView',
  components: { ShopTopContentComponentEdit, SellerProductCarousel},
  data() {
    return {
      shopInfo: null,
      products:[]
    };
  },
  computed: {
    ...mapState({
      shopId: state => state.shopId,
      userInfo: state => state.userInfo,
    }),
  },
  mounted() {
    this.fetchShopInfo();
  },
  methods: {
    ...mapActions(['saveShopId']),

    fetchShopInfo() {
      if (this.userInfo) {
        axios.get(`http://localhost:8085/shop/seller/${this.userInfo.email}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*'
          },
        })
            .then(response => {
              this.shopInfo = response.data[0];
              this.saveShopId(response.data[0].id);
              console.log(this.shopId);
              this.getShopProducts(this.shopId);
            })
            .catch(error => {
              console.log(error);
            });
      } else {
        router.push({name: 'client-home'});
      }
    },

    getShopProducts(shopId) {
      axios.get(`http://localhost:8085/product/products/`+ shopId , {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      }).then(response => {
        console.log(response.data);
        this.products = response.data;
      }).catch(error => {
        console.log(error);
      });
    },

  }
};

</script>
