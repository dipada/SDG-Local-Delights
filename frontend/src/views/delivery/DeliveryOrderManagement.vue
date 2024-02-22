<template>
  <div class="w-screen bg-gray-50">
    <!-- Altri elementi del template -->
    <div class="mx-auto max-w-screen-xl px-2 py-10">
      <div class="mt-6 overflow-hidden rounded-xl bg-white px-6 shadow lg:px-4">
        <table class="min-w-full border-collapse border-spacing-y-2 border-spacing-x-2">
          <thead class="hidden border-b lg:table-header-group">
          <!-- Intestazioni della tabella -->
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
            <td class="whitespace-no-wrap hidden py-4 text-left text-sm text-gray-600 sm:px-3 lg:table-cell">
              {{ order.userEmail }}
            </td>
            <td class="whitespace-no-wrap py-4 text-right text-sm text-gray-600 sm:px-3 lg:text-left">€{{
                order.amount
              }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-500 sm:px-3 lg:table-cell">
                <span :class="getClassForOrderStatus(order.orderStatus)" class="px-2 py-0.5 rounded-full">
                  {{ order.orderStatus }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <!-- Modale per i dettagli dell'ordine -->
  </div>
</template>


<script>
import axios from 'axios';
import {mapActions, mapState} from 'vuex';
import store from "@/store/index.js";

export default {
  data() {
    return {
      orders: [],
      isModalVisible: false,
      orderItems: [],
      actualOrder: null,
    };
  },

  methods: {
    async fetchOrders() {
      try {
        console.log("Fetching orders for delivery email:", this.$store.getters.getUserInfo.email);
        const response = await axios.get(`http://localhost:8085/api/v1/order/orderByDeliveryId/${this.$store.getters.getUserInfo.email}`, {
          headers: {
            'Authorization': 'Bearer ' + store.getters.getUserToken,
            'Accept': '*/*',
          },
        });
        const orderPriority = ["PENDING", "TO_BE_DELIVERED", "TO_BE_PICKED_UP", "IN_TRANSIT", "COMPLETED", "CANCELLED"];
        this.orders = response.data.sort((a, b) => {
          const indexA = orderPriority.indexOf(a.orderStatus);
          const indexB = orderPriority.indexOf(b.orderStatus);

          if (indexA < indexB) {
            return -1;
          }
          if (indexA > indexB) {
            return 1;
          }
          return 0;
        });
      } catch (error) {
        console.error("Error fetching orders:", error);
      }
    },

    // Altri metodi come 'showModal', 'closeModal', ecc.

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
    },

    getClassForOrderStatus(orderStatus) {
      switch (orderStatus) {
        case "PENDING":
          return "bg-yellow-100 text-yellow-800";
        case "TO_BE_DELIVERED":
          return "bg-amber-200 text-amber-800";
        case "TO_BE_PICKED_UP":
          return "bg-blue-100 text-blue-800";
        case "IN_TRANSIT":
          return "bg-blue-100 text-blue-800";
        case "COMPLETED":
          return "bg-green-100 text-green-800";
        case "CANCELLED":
          return "bg-red-100 text-red-800";
        default:
          return "";
      }
    },
  },
  mounted() {
    this.fetchOrders();
  },
};
</script>

