<template>
  <HeaderBase>
    <template #nav>
      <div class="flex items-center">
        <LogoutButtonComponent/>
      </div>
    </template>
  </HeaderBase>
  <div class="w-screen">
    <div class="mx-auto max-w-screen-xl px-2 py-10">
      <h1 v-if="orders && orders.length" class="text-black mb-8 text-4xl font-bold dark:text-gray-400"><b>Your delivery orders:</b></h1>
      <h2 v-else class="mb-8 text-4xl font-bold dark:text-gray-400 text-black">No order here :(</h2>
      <div v-if="orders && orders.length" class="mt-6 overflow-hidden rounded-xl bg-white px-6 shadow lg:px-4">
        <table class="min-w-full border-collapse border-spacing-y-2 border-spacing-x-2">
          <thead class="hidden border-b lg:table-header-group">
          <tr>
            <th class="whitespace-normal py-4 text-sm font-semibold text-gray-800 sm:px-3">Order Date</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Order ID</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Address</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Price</th>
            <th class="whitespace-normal py-4 text-sm font-medium text-gray-500 sm:px-3">Status</th>
          </tr>
          </thead>
          <tbody class="bg-white lg:border-gray-300">
          <tr v-for="order in orders" :key="order.id" @click="showModal(order)" class="cursor-pointer">
            <td class="whitespace-no-wrap py-4 text-left text-sm text-gray-600 sm:px-3 lg:text-left">
              {{ order.timestamp }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-sm font-normal text-gray-600 sm:px-3 lg:table-cell">
              {{ order.id }}
            </td>
            <td class="whitespace-no-wrap hidden py-4 text-left text-sm text-gray-600 sm:px-3 lg:table-cell">
              {{ order.shippingAddress }}
            </td>
            <td class="whitespace-no-wrap py-4 text-right text-sm text-gray-600 sm:px-3 lg:text-left">
              €{{ order.amount }}
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
    <div v-if="isModalVisible" id="popup-modal"
         class="overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 flex justify-center items-center w-full h-full">
      <div class="relative p-4 w-full max-w-md h-auto">
        <div class="relative bg-white rounded-lg shadow dark:bg-gray-700">
          <button type="button"
                  class="absolute top-3 right-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
                  @click="closeModal">
            <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd"
                    d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                    clip-rule="evenodd"></path>
            </svg>
            <span class="sr-only">Close modal</span>
          </button>
          <div class="p-6 text-center">
            <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">Order <b>#{{ actualOrder.id }}</b> will be marked as delivered. Are you sure? </h3>
            <button @click="closeModal" type="button"
                    class="py-2.5 px-5 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">
              No, cancel
            </button>
            <button
                @click="confirmDelivery"
                type="button"
                class="text-white bg-secondary hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:focus:ring-green-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center mr-2"
                :class="{ 'opacity-50 cursor-not-allowed': actualOrder.orderStatus !== 'IN_TRANSIT' }"
                :disabled="actualOrder.orderStatus !== 'IN_TRANSIT'">
              Yes, I'm sure
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
import LogoutButtonComponent from "@/components/LogoutButtonComponent.vue";

export default {
  components: {LogoutButtonComponent, HeaderBase},
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

    showModal(order) {
      this.actualOrder = order;
      this.isModalVisible = true;
    },

    closeModal() {
      this.isModalVisible = false;
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

    confirmDelivery() {
      const status = "COMPLETED";
      axios.put(`http://localhost:8085/api/v1/order/update-order-status/${this.actualOrder.id}`, status, {
        headers: {
          'Authorization': 'Bearer ' + store.getters.getUserToken,
          'Accept': '*/*',
          'Content-Type': 'application/json'
        }
      }).then(() => {
        this.fetchOrders(store.getters.getShopId);
      }).catch((error) => {
        console.error("Error updating order status: ", error);
      });

      this.fetchOrders()
      this.closeModal();
    },

  },

  mounted() {
    this.fetchOrders();
  },
};
</script>