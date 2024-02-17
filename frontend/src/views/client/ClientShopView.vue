<template>
  <HeaderBase class="sticky top-0 z-50">
    <template #nav>
      <CartButtonComponent/>
    </template>
  </HeaderBase>

  <ShopTopContentComponent :shopInfo="shopInfo"/>

  <ProductCarousel :items="shopItemsList"/>
</template>

<script>
import {mapState} from 'vuex';
import HeaderBase from "@/components/HeaderBase.vue";
import ProductCarousel from "@/components/ProductCarousel.vue";
import axios from "axios";
import store from "@/store/index.js";
import router from "@/router/index.js";
import ShopTopContentComponent from "@/components/ShopTopContentComponent.vue";
import CartButtonComponent from "@/components/cartButtonComponent.vue";

export default {
  name: 'ClientShopView',
  components: {CartButtonComponent, ShopTopContentComponent, ProductCarousel, HeaderBase},
  data() {
    return {
      shopInfo: null,
      shopItemsList: [],
    };
  },
  computed: {
    ...mapState({
      shopId: state => state.shopId
    }),
  },
  mounted() {
    this.fetchShopInfo();
    this.fetchShopItems();
  },
  methods: {
    fetchShopItems() {
      console.log('fetching shop items', this.shopId);
      if (this.shopId) {
        axios.get(`http://localhost:8085/product/shop/${this.shopId}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*'
          },
        })
            .then(response => {
              this.shopItemsList = response.data;
            })
            .catch(error => {
              console.log(error);
            });
      }
    },

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
      }
    }
  }
};
</script>