<template>
  <div class="justify-center flex-1 max-w-6xl py-4 mx-auto lg:py-6 md:px-6">
    <div class="flex flex-wrap ">
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0">
        <img v-if="shopInfo" :src="shopInfo.imageUrl" alt="Immagine del Negozio"
             class="relative z-40 object-cover w-full h-96 rounded-3xl">
      </div>
      <div class="w-full px-4 mb-10 lg:w-1/2 lg:mb-0 ">
        <h2 v-if="shopInfo" class="mb-4 text-4xl font-semibold text-secondary dark:text-gray-300">
          {{ shopInfo.name }}
        </h2>
        <h2 v-if="shopInfo" class="mb-4 font-semibold text-secondary dark:text-gray-300">
          {{ shopInfo.description }}
        </h2>
        <div class="w-full mb-10 lg:mb-0 ">
          <ul class="mb-10 text-base leading-7 text-gray-500 dark:text-gray-400">
            <li v-if="shopInfo"><b>Indirizzo:</b> {{ shopInfo.address }}</li>
            <li v-if="shopInfo"><b>Telefono:</b> {{ shopInfo.phoneNumber }}</li>
            <li v-if="shopInfo"><b>Email:</b> {{ shopInfo.shopEmail }}</li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ShopTopContentComponent',
  props: {
    shopInfo: {
      id: Number,
      name: String,
      description: String,
      address: String,
      phoneNumber: String,
      email: String,
      imageUrl: String,
      sellerEmail: String,
      longitude: Number,
      latitude: Number
    }
  }
}
</script>
