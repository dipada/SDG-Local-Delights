<template>
  <HeaderBase class="sticky top-0 z-50">
    <template #nav>
      <CartButtonComponent/>
    </template>
  </HeaderBase>

  <ShopTopContentComponent :shopInfo="shopInfo"/>

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
import CartButtonComponent from "@/components/cartButtonComponent.vue";

export default {
  name: 'ClientShopView',
  components: {CartButtonComponent, ShopTopContentComponent, ProductCarousel, HeaderBase},
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
        axios.get(`/apigateway/shop/get/${this.shopId}`, {
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