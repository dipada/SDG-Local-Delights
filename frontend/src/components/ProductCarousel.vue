<template>
  <section class="flex bg-gray-100 dark:bg-gray-800">
    <div class="p-4 mx-auto max-w-7xl">
      <div class="grid grid-cols-1 gap-4 lg:gap-4 sm:gap-4 sm:grid-cols-2 lg:grid-cols-4">
        <div v-for="item in items" :key="item.id" class="mt-56 bg-white rounded shadow dark:bg-gray-700">
          <div class="relative z-20 p-6 group">
            <div class="relative block h-64 mb-4 -mt-56 overflow-hidden rounded -top-full ">
              <img class="pointer-events-none object-cover w-full h-full transition-all group-hover:scale-110"
                   :src="item.image" :alt="item.name">

              <div @click="addToCart(item.id)" class="absolute flex flex-col top-4 right-4">
                  <div
                      class=" active:animate-ping active:bg-green-800 relative flex items-center justify-center p-3 mb-3 transition-all translate-x-20 bg-white rounded dark:bg-gray-700 dark:text-white group-hover:translate-x-0 wishlist hover:bg-blue-200 dark:hover:bg-blue-600 group">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                         fill="green" class="bi bi-cart2" viewBox="0 0 16 16">
                      <path
                          d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l1.25 5h8.22l1.25-5H3.14zM5 13a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"></path>
                    </svg>
                  </div>
              </div>

            </div>
            <a href="#">
              <h2 class="mb-2 text-xl font-bold text-black dark:text-white">
                {{ item.name }}
              </h2>
            </a>
            <p class="mb-3 text-lg font-bold text-blue-500 dark:text-blue-300 ">
              <span><b class="text-black">price:</b> {{ item.price }} &euro;</span>
            </p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: 'ProductCarousel',
  props: {
    items: {
      type: Array,
      required: true,
    },
  },
  methods: {
    addToCart(productId) {
      console.log('Product added to cart:', productId);
      this.$store.commit('addProductToCart', productId);
    }
  },
};
</script>
