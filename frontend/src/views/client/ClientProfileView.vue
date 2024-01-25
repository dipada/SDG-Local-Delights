<template>
  <HeaderBase />
  <div class="flex-auto justify-center m-20 text-secondary">
    <!-- <AvatarComponent :image-url="user.picture" :name="user.name"/> -->
    <div class="flex flex-col sm:flex-row sm:justify-between  justify-center items-center">
      <img class="rounded w-36 h-36" :src="user.picture" alt="Extra large avatar">
      <button @click="navigateSellerHome" type="button" class="mt-5 h-fit w-fit focus:outline-none text-white bg-green-700 hover:bg-green-800 focus:ring-4 focus:ring-green-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800">Your shops</button>
    </div>
  <user-profile-body-component :email=user.email :full_name="user.name"/>
  <stacked-list-component/>
  </div>
</template>
<script>
import {defineComponent} from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import UserProfileBodyComponent from "@/components/UserProfileBodyComponent.vue";
import StackedListComponent from "@/components/StackedListComponent.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import {jwtDecode} from "jwt-decode";

export default defineComponent({
  components: {AvatarComponent, StackedListComponent, UserProfileBodyComponent, HeaderBase},
data() {
  return {
    user: {
      name: '',
      picture: '',
      email: ''
    }
  };
},

  methods: {
  navigateSellerHome() {
    this.$router.push({name: 'seller-home'});
  }
},
created() {
  const token = localStorage.getItem('userToken');
  if (token) {
    try {
      const tokende = jwtDecode(token);
      this.user.email = tokende.sub;
      this.user.name = tokende.name + " " + tokende.surname;
      this.user.picture = tokende.picture;
    } catch (error) {
      console.error('Token not available:', error);
    }
  }
},
});
</script>