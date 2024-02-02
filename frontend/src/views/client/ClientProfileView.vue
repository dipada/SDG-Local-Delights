<template>
  <HeaderBase />
  <div class="flex-auto justify-center m-20 text-secondary">
    <div class="flex flex-col sm:flex-row sm:justify-between justify-center items-center">
      <img class="rounded w-36 h-36" :src="userInfo.picture" alt="Extra large avatar">
      <button @click="navigateSellerHome" type="button" class="mt-5 h-fit w-fit focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Your shops</button>
    </div>
    <user-profile-body-component :email="userInfo.email" :full_name="userInfo.name + ' ' + userInfo.surname" />
    <stacked-list-component />
  </div>
</template>

<script>
import { defineComponent, computed } from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import UserProfileBodyComponent from "@/components/UserProfileBodyComponent.vue";
import StackedListComponent from "@/components/StackedListComponent.vue";

export default defineComponent({
  components: {StackedListComponent, UserProfileBodyComponent, HeaderBase},
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
