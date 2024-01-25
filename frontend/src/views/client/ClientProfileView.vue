<template>
  <HeaderBase />
  <div class="flex-auto justify-center m-20 text-secondary">
    <!-- <AvatarComponent :image-url="user.picture" :name="user.name"/> -->
    <img class="rounded w-36 h-36" :src="user.picture" alt="Extra large avatar">
    {{user.email}}
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