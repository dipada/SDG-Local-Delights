<template>
  <HeaderBase/>
  <div class="flex-auto justify-center m-20 text-secondary">
    <div class="flex flex-col sm:flex-row sm:justify-between justify-center items-center">
      <img class="rounded w-36 h-36" :src="userInfo.picture" alt="Extra large avatar">
      <div class="mt-4">
        <b>Your balance</b>: {{ money }} &euro;
        <div class="flex justify-center items-center">
          <div class="flex flex-col sm:flex-row sm:space-x-4 w-full max-w-xs sm:max-w-md lg:max-w-lg xl:max-w-xl mx-auto">
            <input v-model="topUpAmount" type="number" placeholder="Amount"
                   class="h-12 w-full rounded-md bg-gray-100 px-3"/>
            <button @click="makeTopUp" type="button"
                    class="mt-4 sm:mt-0 h-12 w-full min-w-max sm:w-auto focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm flex justify-center items-center px-5 py-2.5 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">
              Top up
            </button>
          </div>
        </div>

      </div>
      <button @click="navigateSellerShop" type="button"
              class="mt-5 h-fit w-fit focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">
        Your shop
      </button>
    </div>
    <user-profile-body-component :email="userInfo.email" :full_name="userInfo.name + ' ' + userInfo.surname"/>

    <OrderListComponent v-if="orderDetails && orderDetails.length" :orders="orderDetails"/>
    <div v-else class="w-screen">
      <div class="mt-8 max-w-screen-lg">
        <div class="sm:flex sm:items-center sm:justify-between flex-col sm:flex-row">
          <p class="flex-1 text-base font-bold text-gray-900 ">No orders here :(</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {defineComponent} from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import UserProfileBodyComponent from "@/components/UserProfileBodyComponent.vue";
import OrderListComponent from "@/components/OrderListComponent.vue";
import axios from "axios";

export default defineComponent({
  components: {OrderListComponent, UserProfileBodyComponent, HeaderBase},

  data() {
    return {
      topUpAmount: 0,
      money: 0,
      orderDetails: [],
    }
  },

  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  methods: {
    async makeTopUp() {
      console.log("Ricarica di", this.topUpAmount, "euro");
      await axios.post(`http://localhost:8085/payment/topup`, {
            email: this.$store.state.userInfo.email,
            amount: this.topUpAmount,
            //orderId: ""
          }, {
            headers: {
              'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
              'Accept': '*/*'
            },
          }
      )
          .then(response => {
            console.log("Ricarica effettuata con successo");
            this.fetchUserBalance();
            this.topUpAmount = 0;
          })
          .catch(error => {
            console.log(error);
          });
    },

    navigateSellerShop() {
      this.$router.push({name: 'seller-home'});
    },

    fetchUserBalance() {
      axios.get(`http://localhost:8085/payment/balance/${this.userInfo.email}`, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      })
          .then(response => {
            console.log("User balance: ", response.data);
            this.money = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    },

    fetchOrderDetails() {
      console.log(this.userInfo.email);
      axios.get(`http://localhost:8085/api/v1/order/get-all-orders/${this.userInfo.email}`, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      })
          .then(response => {
            //this.orderDetails = response.data;
            const userOrders = response.data;
            return Promise.all(userOrders.map(userOrders => this.getOrderDetails(userOrders)))
          })
          .then(orderDetails => {
            this.orderDetails = orderDetails;
          })
          .catch(error => {
            console.log(error);
          });
    },

    getOrderDetails(userOrders) {
      return axios.get(`http://localhost:8085/shop/get/${userOrders.shopId}`, {
        headers: {
          'Authorization': 'Bearer ' + this.$store.getters.getUserToken,
          'Accept': '*/*'
        },
      })
          .then(response => {
            return {
              shopname: response.data.name,
              date: userOrders.timestamp,
              amount: userOrders.amount,
              status: userOrders.orderStatus
            }
          })
          .catch(error => {
            console.log(error);
          });
    },
  },

  mounted() {
    this.fetchOrderDetails();
    this.fetchUserBalance();
  }
});

</script>
