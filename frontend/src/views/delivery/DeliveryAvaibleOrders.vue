<template>
  <ul class="max-w-md divide-y divide-gray-200 dark:divide-gray-700">
    <li v-for="order in orders" :key="order.id" class="py-3 sm:py-4">
      <div class="flex items-center space-x-4 rtl:space-x-reverse">
        <div class="flex-shrink-0">
          <img class="w-8 h-8 rounded-full" :src="order.shopImage" :alt="order.shopName">
        </div>
        <div class="flex-1 min-w-0">
          <p class="text-sm font-medium text-gray-900 truncate dark:text-white">
            {{ order.shopName }}
          </p>
          <p class="text-sm text-gray-500 truncate dark:text-gray-400">
            {{ order.deliveryAddress }}
          </p>
        </div>
        <button @click="takeOrder(order.id)" class="inline-flex items-center px-3 py-2 border border-transparent text-sm leading-4 font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
          Take Order
        </button>
      </div>
    </li>
  </ul>
</template>



<script>
import axios from 'axios';
import store from "@/store/index.js";

export default {
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
            const response = await axios.get(`http://localhost:8085/shop/get/${shopId}`,{
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
      try {
        await axios.post('/your-post-endpoint', { orderId });
        // Aggiungi qui la logica per gestire la risposta
      } catch (error) {
        console.error("There was an error taking the order:", error);
      }
    },
  },
};
</script>

