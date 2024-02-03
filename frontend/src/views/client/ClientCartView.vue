<template>
  <HeaderBase>
    <template #nav>
      <div class="flex lg:flex-1 justify-end">
        <div class="flex items-center gap-4">
          <AvatarComponent class="pr-4" :image-url="userInfo.picture" :name="userInfo.name"/>
          <logout-button-component/>
        </div>
      </div>
    </template>
  </HeaderBase>

  <h1 class="text-black">wewe {{cartItems}}</h1>


</template>

<script>
import HeaderBase from "@/components/HeaderBase.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import CartButtonComponent from "@/components/cartButtonComponent.vue";
import LogoutButtonComponent from "@/components/LogoutButtonComponent.vue";

export default {
  name: 'ClientCartView',
  components: {LogoutButtonComponent, CartButtonComponent, AvatarComponent, HeaderBase},
  computed: {
    cartItems() {
      console.log("cartItems", this.$store.getters.getCartProducts)
      return this.$store.getters.getCartProducts || [];
    },
    orderTotal() {
      return this.cartItems.reduce((total, item) => total + (item.price * item.quantity), 0);
    },
    userInfo() {
      return this.$store.getters.getUserInfo;
    }
  },
  methods: {
    calculateSubtotal(item) {
      return item.price * item.quantity;
    },
    incrementQuantity(item) {
      this.$store.commit('incrementProductQuantity', item.id);
    },

    decrementQuantity(item) {
      if (item.quantity > 1) {
        this.$store.commit('decrementProductQuantity', item.id);
      } else {
        this.removeCartItem(item);
      }
    },

    removeCartItem(item) {
      this.$store.commit('removeProductFromCart', item.id);
    }
  }
}
</script>
