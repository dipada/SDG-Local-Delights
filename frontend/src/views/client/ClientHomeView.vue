<template>
  <HeaderBase>
    <template #nav>
      <cart-button-component />
      <AvatarComponent :image-url="userInfo.picture" :name="userInfo.name" />
    </template>
  </HeaderBase>
  <client-home-body-component />
</template>

<script>
import { defineComponent, computed } from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import CartButtonComponent from "@/components/cartButtonComponent.vue";
import ClientHomeBodyComponent from "@/components/ClientHomeBodyComponent.vue";

export default defineComponent({
  components: { ClientHomeBodyComponent, CartButtonComponent, HeaderBase, AvatarComponent },
  computed: {
    userInfo() {
      return this.$store.state.userInfo;
    }
  }
});
</script>
