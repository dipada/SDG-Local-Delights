<template>
  <HeaderBase/>
  <div class="flex-auto justify-center m-20 text-secondary">
    <div class="flex flex-col sm:flex-row sm:justify-between justify-center items-center">
      <img class="rounded w-36 h-36" :src="userInfo.picture" alt="Extra large avatar">
      <button @click="navigateSellerHome" type="button"
              class="mt-5 h-fit w-fit focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">
        Your shops
      </button>
    </div>
    <user-profile-body-component :email="userInfo.email" :full_name="userInfo.name + ' ' + userInfo.surname"/>
    <OrderListComponent :orders="testOrders"/>
    <OrderListComponent :orders="orderDetails"/>
  </div>

  <div class="text-black">
    test -->
    {{ testOrders }}
  </div>

  <div class="text-black">
    da get -->
    {{ orderDetails }}
  </div>
</template>

<script>
import {defineComponent} from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import UserProfileBodyComponent from "@/components/UserProfileBodyComponent.vue";
import OrderListComponent from "@/components/OrderListComponent.vue";
import axios from "axios";
import {mapState} from "vuex";

export default defineComponent({
  components: {OrderListComponent, UserProfileBodyComponent, HeaderBase},

  data() {
    // 3 orders for testing purposes
    return {
      orderDetails: [],
      // TODO abbinare alla get Orders e reperire info mancanti, nome negozio da id, convertire timestamp in data
      // TODO aggiungere amount su be
      testOrders: [
        {
          shopname: "Shop1",
          date: "2021-10-10",
          amount: "100",
          status: "completed"
        },
        {
          shopname: "Shop2",
          date: "2021-10-11",
          amount: "200",
          status: "cancelled"
        },
        {
          shopname: "Shop3",
          date: "2021-10-12",
          amount: "300",
          status: "to_be_delivered"
        },
        {
          shopname: "Shop3",
          date: "2021-10-12",
          amount: "300",
          status: "pending"
        }
      ]
    }
  },

  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    },
  },
  methods: {
    navigateSellerHome() {
      this.$router.push({name: 'seller-home'});
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
  }
});

</script>
