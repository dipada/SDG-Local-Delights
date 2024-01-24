<script>
import { defineComponent } from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import {jwtDecode} from "jwt-decode";
import CartButtonComponent from "@/components/cartButtonComponent.vue";
import ClientHomeBodyComponent from "@/components/ClientHomeBodyComponent.vue";

export default defineComponent({
  components: {ClientHomeBodyComponent, CartButtonComponent, HeaderBase, AvatarComponent },
  data() {
    return {
      user: {
        name: '',
        picture: ''
      }
    };
  },
  created() {
    const token = localStorage.getItem('userToken');
    if (token) {
      try {
        const tokende = jwtDecode(token);
        this.user.name = tokende.name + " " + tokende.surname;
        this.user.picture = tokende.picture;
      } catch (error) {
        console.error('Token not available:', error);
      }
    }
  }
});
</script>

<template>
  <HeaderBase>
    <template #nav>
      <cart-button-component/>
      <AvatarComponent :image-url="user.picture" :name="user.name"/>
    </template>
  </HeaderBase>
  <client-home-body-component/>
</template>
