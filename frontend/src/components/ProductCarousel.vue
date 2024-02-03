<template>
  <section class="flex bg-gray-100 dark:bg-gray-800">
    <div class="p-4 mx-auto max-w-7xl">
      <div class="grid grid-cols-1 gap-4 lg:gap-4 sm:gap-4 sm:grid-cols-2 lg:grid-cols-4">
        <!-- Iterazione sugli articoli -->
        <div v-for="item in items" :key="item.id" class="mt-56 bg-white rounded shadow dark:bg-gray-700">
          <div class="relative z-20 p-6 group">
            <div class="relative block h-64 mb-4 -mt-56 overflow-hidden rounded -top-full ">
              <img class="object-cover w-full h-full transition-all group-hover:scale-110"
                   :src="item.imageUrl" :alt="item.name">

              <div @click="addToCart(item.id)" class="absolute flex flex-col top-4 right-4">
                <a href="#" class="flex items-center">
                  <div
                      class="relative flex items-center justify-center p-3 mb-3 transition-all translate-x-20 bg-white rounded dark:bg-gray-700 dark:text-white group-hover:translate-x-0 wishlist hover:bg-blue-200 dark:hover:bg-blue-600 group">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20"
                         fill="green" class="bi bi-cart2" viewBox="0 0 16 16">
                      <path
                          d="M0 2.5A.5.5 0 0 1 .5 2H2a.5.5 0 0 1 .485.379L2.89 4H14.5a.5.5 0 0 1 .485.621l-1.5 6A.5.5 0 0 1 13 11H4a.5.5 0 0 1-.485-.379L1.61 3H.5a.5.5 0 0 1-.5-.5zM3.14 5l1.25 5h8.22l1.25-5H3.14zM5 13a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0zm9-1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm-2 1a2 2 0 1 1 4 0 2 2 0 0 1-4 0z"></path>
                    </svg>
                  </div>
                </a>
              </div>

            </div>
            <a href="#">
              <h2 class="mb-2 text-xl font-bold text-black dark:text-white">
                {{ item.name }}
              </h2>
            </a>
            <p class="mb-3 text-lg font-bold text-blue-500 dark:text-blue-300 ">
              <span>{{ item.price }}</span>
              <!-- ... -->
            </p>
            <!-- ... altro codice per il contenuto del prodotto ... -->
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
    items: Array, // Array di articoli passato come prop
  },
  data() {
    return {
      // Qui puoi aggiungere dati aggiuntivi o metodi specifici del componente
      items: [
        {
          id: 1,
          name: 'Fotocamera Reflex Digitale',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '899.00€',
          description: 'Una fotocamera reflex digitale ad alta risoluzione, ideale per fotografia paesaggistica e ritratti.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 2,
          name: 'Smartphone 5G',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '599.00€',
          description: 'Uno smartphone all\'avanguardia con connettività 5G, fotocamera di alta qualità e design elegante.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
        {
          id: 3,
          name: 'Smartwatch Fitness',
          imageUrl: 'https://upload.wikimedia.org/wikipedia/commons/thumb/f/ff/Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg/2560px-Smart_ForTwo_-_Flickr_-_mick_-_Lumix.jpg',
          price: '249.99€',
          description: 'Uno smartwatch perfetto per il fitness con monitoraggio della frequenza cardiaca, GPS integrato e resistenza all\'acqua.'
        },
      ]
    };
  },
  methods: {
    addToCart(productId) {
      console.log('Product added to cart:', productId);
      this.$store.commit('addProductToCart', productId);
      //this.$store.commit('cart/addProduct', productId);
    }
  },
};
</script>
