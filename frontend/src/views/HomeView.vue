<script setup>
import HeaderHomeComponent from "@/components/HeaderHomeComponent.vue";
import CardHome from "@/components/CardHome.vue";
import fruitImg from "@/assets/fruit.svg";
import vegetablesImg from "@/assets/vegetables.svg";
import pastaImg from "@/assets/pasta.svg";
import breadImg from "@/assets/bread.svg";
import eggsImg from "@/assets/eggs.svg";
import meatImg from "@/assets/meat.svg";
import bellCrocierieImg from "@/assets/bell-concierge-solid.svg";
import creditCardImg from "@/assets/credit-card-solid.svg";
import locationDotImg from "@/assets/location-dot-solid.svg";
import shoppingBagImg from "@/assets/shoppingBag.svg";

</script>

<template>
  <HeaderHomeComponent class="z-10"/>
  <div class="flex flex-col items-center justify-center bg-neutral-100">
    <img width="1280" src="../assets/cover.jpg" alt="">
  </div>
<div class="flex-col justify-center mx-auto items-center">
  <h2 class="py-10 text-3xl font-bold text-center text-green-800">Large variety of products</h2>

  <div class="container flex flex-col md:flex-row justify-center mx-auto items-center">
    <CardHome class="m-4" :image="fruitImg" title="Fruit"/>
    <CardHome class="m-4" :image="vegetablesImg" title="Vegetables"/>
    <CardHome class="m-4" :image="pastaImg" title="Pasta"/>
    <CardHome class="m-4" :image="breadImg" title="Bread"/>
    <CardHome class="m-4" :image="eggsImg" title="Eggs"/>
    <CardHome class="m-4" :image="meatImg" title="Meat"/>
  </div>
</div>
  <div class="flex-col justify-center mx-auto items-center">
    <h2 class="py-10 text-3xl font-bold text-center text-green-800">How does it works</h2>

    <div class="container flex flex-col md:flex-row justify-center mx-auto items-center">
      <CardHome class="border-none shadow-none m-4" :image="locationDotImg" title="Select location" description="Choose the location where your food will be delivered"/>
      <CardHome class="border-none shadow-none m-4" :image="bellCrocierieImg" title="Chose order" description="Choose your favourite products from all the options"/>
      <CardHome class="border-none shadow-none m-4" :image="creditCardImg" title="Pay advanced" description="It's quick, safe, and simple. Select several methods of payment"/>
      <CardHome class="border-none shadow-none m-4" :image="shoppingBagImg" title="Enjoy meal" description="Wait your order or pickup directly in the shop"/>
    </div>
  </div>
</template>

<style scoped>

</style>