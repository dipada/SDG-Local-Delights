<template>
  <HeaderBase/>
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
          <tr v-for="order in orders" :key="order.id" @click="showModal(order)" class="cursor-pointer">
            <td class="whitespace-no-wrap py-4 text-left text-sm text-gray-600 sm:px-3 lg:text-left">{{
                order.timestamp
              }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-600 sm:px-3 lg:table-cell">
              {{ order.id }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-600 sm:px-3 lg:table-cell">Multiple
              Products
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-left text-sm text-gray-600 sm:px-3 lg:table-cell">
              {{ order.userEmail }}
            </td>
            <td class="whitespace-no-wrap py-4 text-right text-sm text-gray-600 sm:px-3 lg:text-left">€{{
                order.amount
              }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-500 sm:px-3 lg:table-cell">
                <span
                    :class="{'bg-purple-100 text-purple-800': order.orderStatus === 'PENDING', 'bg-green-100 text-green-800': order.orderStatus !== 'PENDING'}"
                    class="px-2 py-0.5 rounded-full">
                  {{ order.orderStatus }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div v-if="isModalVisible"
         class="overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full flex"
         @click.self="closeModal">
      <div class="relative p-4 w-full max-w-md max-h-full mx-auto z-10">
        <div class="relative text-black bg-white rounded-lg shadow dark:bg-gray-700">
          <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 class="text-lg font-semibold text-gray-900 dark:text-white">
              Order Items fff
            </h3>
            <button @click="closeModal" type="button"
                    class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm h-8 w-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
              <span class="sr-only">Close</span>
              <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
                   xmlns="http://www.w3.org/2000/svg">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
              </svg>
            </button>
          </div>
          <div class="p-4 md:p-5">
            <p>Order details:</p>
            <ul>
              <li v-for="item in orderItems" :key="item.id" class="border-b last:border-b-0">
                <div class="flex items-center justify-between p-3">
                  <div class="flex items-center">
                    <img :src="item.image" alt="product image" class="w-10 h-10 mr-3">
                    <div>
                      <div class="font-semibold">{{ item.name }}</div>
                      <div class="text-gray-500">Quantity: {{ item.quantity }}</div>
                    </div>
                  </div>
                  <div class="text-gray-900">€{{ item.price }}</div>
                </div>
              </li>
            </ul>
            <button class="mt-5 w-full rounded-md bg-secondary p-2 text-center font-semibold text-white"
                    :class="{ 'opacity-50': isOrderReady }"
                    @click="orderReady"
                    :disabled="isOrderReady">Ready
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import store from "@/store/index.js";
import HeaderBase from "@/components/HeaderBase.vue";

export default {
  components: {HeaderBase},
  data() {
    return {
      orders: [],
      isModalVisible: false,
      orderItems: [],
      isOrderReady: false,
      actualOrder: null,
    };
  },
  methods: {

    orderReady() {
      console.log('Order is ready');
      const status = "TO_BE_DELIVERED";
      axios.put(`http://localhost:8085/api/v1/order/update-order-status/${this.actualOrder.id}`, status, {
            headers: {
              'Authorization': 'Bearer ' + store.getters.getUserToken,
              'Accept': '*/*',
              'Content-Type': 'application/json'
            }
          }).then(() => {
        this.isOrderReady = true;
        this.fetchOrders(store.getters.getShopId);
      }).catch((error) => {
        console.error("Error updating order status: ", error);
        this.isOrderReady = false;
      });
    },

    async fetchOrders(shopId) {
      try {
        const response = await axios.get(`http://localhost:8085/api/v1/order/orderByShopId/${shopId}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        this.orders = response.data.sort((a, b) => a.orderStatus === "PENDING" && b.orderStatus !== "PENDING" ? -1 : 1);
      } catch (error) {
        console.error("Errore durante il fetch degli ordini:", error);
      }
    },

    async showModal(order) {
      console.log('Showing modal for order:', order);
      this.actualOrder = order;
      this.orderItems = [];
      const productIds = [...new Set(order.listOfProductsIds)];
      console.log('Product IDs:', productIds);
      const productDetailsPromises = productIds.map(async (id) => {
        const productDetails = await axios.get(`http://localhost:8085/product/get/${id}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        const quantity = order.listOfProductsIds.filter(productId => productId === id).length;
        return {...productDetails.data, quantity};
      });
      try {
        this.orderItems = await Promise.all(productDetailsPromises);
        console.log('Final orderItems:', this.orderItems);
      } catch (error) {
        console.error("Error fetching products: ", error);
      }
      this.isModalVisible = true;
    },


    closeModal() {
      this.isModalVisible = false;
      this.orderItems = [];
      this.actualOrder = null;
    }
  },


  mounted() {
    this.fetchOrders(store.getters.getShopId);
  }
};
</script>