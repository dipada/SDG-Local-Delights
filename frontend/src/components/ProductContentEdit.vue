<template>
  <div class="mx-auto max-w-screen-lg">
    <div class="relative h-56 rounded-b-lg bg-cover bg-center bg-no-repeat shadow-lg">
      <!-- Immagine di sfondo per il negozio, assicurati di avere un'immagine valida o rimuovi lo style se non necessario -->
      <div class="px-4 pt-8 pb-10">
        <!-- Elementi UI del negozio -->
      </div>
    </div>

    <!-- Header e altre informazioni del negozio qui -->

    <main class="grid grid-cols-2 gap-x-6 gap-y-10 px-2 pb-20 sm:grid-cols-3 sm:px-8 lg:mt-16 lg:grid-cols-4 lg:gap-x-4 lg:px-0">
      <!-- Utilizzo di v-for per iterare su ogni prodotto -->
      <article class="relative" v-for="prodotto in prodotti" :key="prodotto.id">
        <div class="aspect-square overflow-hidden">
          <img class="h-full w-full object-cover transition-all duration-300 group-hover:scale-125"
               :src="prodotto.imageUrl" :alt="prodotto.nome"/>
        </div>
        <div v-if="prodotto.sconto" class="absolute top-0 m-1 rounded-full bg-white">
          <p class="rounded-full bg-black p-1 text-[10px] font-bold uppercase tracking-wide text-white sm:py-1 sm:px-3">
            Sale</p>
        </div>
        <div class="mt-4 flex items-start justify-between">
          <div>
            <h3 class="text-xs font-semibold sm:text-sm md:text-base">
              <a href="#" class="">{{ prodotto.nome }}</a>
            </h3>
            <div class="mt-2 flex items-center">
              <!-- Valutazione in stelle qui, se applicabile -->
            </div>
          </div>
          <div class="text-right">
            <del v-if="prodotto.prezzoVecchio" class="mt-px text-xs font-semibold text-gray-600 sm:text-sm">
              {{ prodotto.prezzoVecchio }}
            </del>
            <p class="text-xs font-normal sm:text-sm md:text-base">{{ prodotto.prezzoAttuale }}</p>
          </div>
        </div>
      </article>
    </main>
  </div>
</template>

<script>
import axios from "axios";

export default {
      prodotti: [{
        id: 1,
        nome: "Arabian Musk",
        imageUrl: "path/to/image.jpg",
        prezzoAttuale: "$49.00",
        prezzoVecchio: "$79.00",
        sconto: true
      },
        {
          id: 2,
          nome: "Arabian Musk",
          imageUrl: "path/to/image.jpg",
          prezzoAttuale: "$49.00",
          prezzoVecchio: "$79.00",
          sconto: true
        },]
  }
</script>