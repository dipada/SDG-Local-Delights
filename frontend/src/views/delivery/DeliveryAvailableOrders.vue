<template>
  <HeaderBase>
    <template #nav>
      <div class="flex flex-row justify-end px-4 py-2">
        <AvatarComponent class="pr-4" :image-url="userInfo.picture" :name="userInfo.name"/>
        <DeliveryOrderButtonComponent class="p"/>
        <logout-button-component class="p"/>
      </div>
    </template>
  </HeaderBase>
  <div class="flex flex-col justify-center items-center">
    <h1 v-if="orders && orders.length" class="mb-8 text-4xl font-bold dark:text-gray-400 text-black"><b>Your delivery orders:</b>
    </h1>
    <h2 v-else class="mb-8 text-4xl font-bold dark:text-gray-400 text-black">No available orders to pick :(</h2>
    <div v-if="orders && orders.length" class="mt-6 overflow-hidden rounded-xl bg-white px-6 shadow lg:px-4">
      <ul class="max-w-full divide-y divide-gray-200 dark:divide-gray-700 mx-auto">
        <li v-for="order in orders" :key="order.id" class="py-3 sm:py-4">
          <div
              class="flex flex-col sm:flex-row items-center space-x-0 sm:space-x-4 rtl:space-x-reverse space-y-4 sm:space-y-0">
            <div class="flex-shrink-0">
              <img class="w-8 h-8 rounded-full" :src="order.shopImage" :alt="order.shopName">
            </div>
            <div class="flex-1 min-w-0 text-center sm:text-left">
              <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
                {{ order.shopName }}
              </p>
              <p class="text-sm text-gray-500 truncate dark:text-gray-400">
                {{ order.deliveryAddress }}
              </p>
            </div>
            <button @click="takeOrder(order.id)"
                    class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
              Take Order
            </button>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>


<script>
import axios from 'axios';
import store from "@/store/index.js";
import HeaderBase from "@/components/HeaderBase.vue";
import DeliveryButtonComponent from "@/components/DeliveryButtonComponent.vue";
import CartButtonComponent from "@/components/cartButtonComponent.vue";
import LogoutButtonComponent from "@/components/LogoutButtonComponent.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import DeliveryOrderButtonComponent from "@/components/DeliveryOrderButtonComponent.vue";

export default {
  components: {
    DeliveryOrderButtonComponent,
    AvatarComponent, LogoutButtonComponent, CartButtonComponent, DeliveryButtonComponent, HeaderBase
  },
  data() {
    return {
      orders: [],
      shopDetailsCache: {}, // Cache shops details to avoid fetching them multiple times
    };
  },
  mounted() {
    this.fetchOrders();
  },
  methods: {
    async fetchOrders() {
      try {
        const response = await axios.get('http://localhost:8085/api/v1/order/get-to-be-delivered-orders', {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        const orders = response.data;
        await this.fetchShopsDetails(orders);
      } catch (error) {
        console.error("There was an error fetching the orders:", error);
      }
    },

    async fetchShopsDetails(orders) {
      // Get all unique shopIds from the orders
      const shopIds = [...new Set(orders.map(order => order.shopId))];

      // Fetch shop details for each shopId
      await Promise.all(shopIds.map(async (shopId) => {
        if (!this.shopDetailsCache[shopId]) {
          try {
            const response = await axios.get(`http://localhost:8085/shop/get/${shopId}`, {
              headers: {
                'Authorization': 'Bearer ' + store.getters.getUserToken,
                'Accept': '*/*',
              },
            });
            this.shopDetailsCache[shopId] = {
              shopName: response.data.name,
              shopImage: response.data.imageUrl,
            };
          } catch (error) {
            console.error(`There was an error fetching details for shopId ${shopId}:`, error);
          }
        }
      }));

      this.orders = orders.map(order => ({
        ...order,
        deliveryAddress: order.shippingAddress,
        ...this.shopDetailsCache[order.shopId],
      }));
    },

    async takeOrder(orderId) {
      const deliveryEmail = this.$store.state.userInfo.email;
      const url = `http://localhost:8085/api/v1/order/take-order/${orderId}?deliveryEmail=${deliveryEmail}`;

      try {
        const response = await axios.post(url, {}, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        console.log("Order taken successfullyFE", response.data);
        await this.fetchOrders();
      } catch (error) {
        console.error("There was an error taking the order:", error.response ? error.response.data : error.message);
      }
    },
  },

  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    }
  }
};
</script>

