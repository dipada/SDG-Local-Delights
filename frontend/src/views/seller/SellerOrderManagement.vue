<template>
  <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro:ital,wght@0,300;0,400;1,600&display=swap" rel="stylesheet" />
  <div class="w-screen bg-gray-50">
    <div class="mx-auto max-w-screen-xl px-2 py-10">
      <div class="mt-6 overflow-hidden rounded-xl bg-white px-6 shadow lg:px-4">
        <table class="min-w-full border-collapse border-spacing-y-2 border-spacing-x-2">
          <thead class="hidden border-b lg:table-header-group">
          <tr>
            <th class="whitespace-normal py-4 text-sm font-semibold text-gray-800 sm:px-3">Order Date</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Order ID</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Description</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Customer</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Price</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Status</th>
          </tr>
          </thead>
          <tbody class="bg-white lg:border-gray-300">
          <tr v-for="order in orders" :key="order.id">
            <td class="whitespace-no-wrap py-4 text-left text-sm text-gray-600 sm:px-3 lg:text-left">
              {{ order.timestamp }}
              <div class="mt-1 flex flex-col text-xs font-medium lg:hidden">
                <div class="flex items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" class="mr-1 h-3 w-3" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                  {{ order.userEmail }}
                </div>
                <div class="flex items-center">
                  <svg xmlns="http://www.w3.org/2000/svg" class="mr-1 h-3 w-3" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                    <path stroke-linecap="round" stroke-linejoin="round" d="M4 6h16M4 10h16M4 14h16M4 18h16" />
                  </svg>
                  Multiple Products
                </div>
              </div>
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-600 sm:px-3 lg:table-cell">{{ order.id }}</td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-600 sm:px-3 lg:table-cell">Multiple Products</td>
            <td class="whitespace-no-wrap hidden py-4 text-left text-sm text-gray-600 sm:px-3 lg:table-cell lg:text-left">{{ order.userEmail }}</td>
            <td class="whitespace-no-wrap py-4 text-right text-sm text-gray-600 sm:px-3 lg:text-left">€{{ order.amount }}
              <span class="mt-1 ml-auto block w-fit whitespace-nowrap rounded-full bg-purple-100 px-2 py-0.5 text-center text-xs text-purple-800 lg:hidden">{{ order.orderStatus }}</span>
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-500 sm:px-3 lg:table-cell">
              <span class="ml-2 mr-3 whitespace-nowrap rounded-full px-2 py-0.5" :class="{'bg-purple-100 text-purple-800': order.orderStatus === 'PENDING', 'bg-green-100 text-green-800': order.orderStatus !== 'PENDING'}">{{ order.orderStatus }}</span>
            </td>
            <td>asddasasd</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import store from "@/store/index.js";
import {DialogOverlay} from "@headlessui/vue";

export default {
  components: {DialogOverlay},
  data() {
    return {
      orders: []
    };
  },
  methods: {
    async fetchOrders(shopId) {
      try {
        const response = await axios.get(`http://localhost:8085/api/v1/order/orderByShopId/${shopId}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        this.orders = response.data.sort((a, b) => {
          if (a.orderStatus === "PENDING" && b.orderStatus !== "PENDING") {
            return -1;
          } else if (a.orderStatus !== "PENDING" && b.orderStatus === "PENDING") {
            return 1;
          } else {
            return new Date(a.timestamp) - new Date(b.timestamp);
          }
        });
      } catch (error) {
        console.error("Errore durante il fetch degli ordini:", error);
      }
    }
  },
  mounted() {
    this.fetchOrders(this.$store.getters.getShopId);
  }
};
</script>