<template>
  <HeaderBase />
  <div class="flex-auto justify-center m-20 text-secondary">
    <div class="flex flex-col sm:flex-row sm:justify-between justify-center items-center">
      <img class="rounded w-36 h-36" :src="userInfo.picture" alt="Extra large avatar">
      <button @click="navigateSellerHome" type="button" class="mt-5 h-fit w-fit focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Your shops</button>
    </div>
    <user-profile-body-component :email="userInfo.email" :full_name="userInfo.name + ' ' + userInfo.surname" />
    <OrderListComponent :orders="testOrders"/>
  </div>
</template>

<script>
import { defineComponent } from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import UserProfileBodyComponent from "@/components/UserProfileBodyComponent.vue";
import OrderListComponent from "@/components/OrderListComponent.vue";

export default defineComponent({
  components: {OrderListComponent, UserProfileBodyComponent, HeaderBase},

  data(){
    // 3 orders for testing purposes
    return {
      // TODO abbinare alla get Orders e reperire info mancanti
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
    }
  },
  methods: {
    navigateSellerHome() {
      this.$router.push({name: 'seller-home'});
    }
  }
});
</script>
