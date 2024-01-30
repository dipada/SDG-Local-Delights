<template>
  <HeaderBase>
    <template #nav>
      <div class="flex flex-row justify-end px-4 py-2">
        <AvatarComponent class="pr-4" :image-url="userInfo.picture" :name="userInfo.name"/>
        <cart-button-component class="pr-4"/>
        <logout-button-component class="p"/>
      </div>
    </template>
  </HeaderBase>
  <div class="flex-col justify-center items-center mx-auto mt-20 min-w-full">
    <client-home-body-component/>
  </div>

  <MapShopsView/>

  <div class="flex-col justify-center mx-auto items-center">
    <h2 class="py-10 text-3xl font-bold text-center text-green-800">Maybe you can find:</h2>

    <div class="container flex flex-col md:flex-row justify-center mx-auto items-center">
      <CardHome class="m-4" :image="fruitImg" title="Fruit"/>
      <CardHome class="m-4" :image="pastaImg" title="Pasta"/>
      <CardHome class="m-4" :image="breadImg" title="Bread"/>
      <CardHome class="m-4" :image="eggsImg" title="Eggs"/>
      <CardHome class="m-4" :image="meatImg" title="Meat"/>
    </div>
  </div>
</template>

<script>
import {defineComponent, computed} from "vue";
import HeaderBase from "@/components/HeaderBase.vue";
import AvatarComponent from "@/components/avatarComponent.vue";
import CartButtonComponent from "@/components/cartButtonComponent.vue";
import ClientHomeBodyComponent from "@/components/ClientHomeBodyComponent.vue";
import LogoutButtonComponent from "@/components/LogoutButtonComponent.vue";
import CardHome from "@/components/CardHome.vue";
import locationDotImg from "@/assets/location-dot-solid.svg";
import bellCrocierieImg from "@/assets/bell-concierge-solid.svg";
import creditCardImg from "@/assets/credit-card-solid.svg";
import shoppingBagImg from "@/assets/shoppingBag.svg";
import fruitImg from "@/assets/fruit.svg";
import vegetablesImg from "@/assets/vegetables.svg";
import pastaImg from "@/assets/pasta.svg";
import breadImg from "@/assets/bread.svg";
import eggsImg from "@/assets/eggs.svg";
import meatImg from "@/assets/meat.svg";
import MapShopsView from "@/views/client/MapShopsView.vue";

export default defineComponent({
  components: {
    MapShopsView,
    CardHome,
    LogoutButtonComponent, ClientHomeBodyComponent, CartButtonComponent, HeaderBase, AvatarComponent},
  computed: {
    meatImg() {
      return meatImg
    },
    eggsImg() {
      return eggsImg
    },
    breadImg() {
      return breadImg
    },
    pastaImg() {
      return pastaImg
    },
    vegetablesImg() {
      return vegetablesImg
    },
    fruitImg() {
      return fruitImg
    },
    shoppingBagImg() {
      return shoppingBagImg
    },
    creditCardImg() {
      return creditCardImg
    },
    bellCrocierieImg() {
      return bellCrocierieImg
    },
    locationDotImg() {
      return locationDotImg
    },
    userInfo() {
      return this.$store.state.userInfo;
    }
  }
});
</script>
