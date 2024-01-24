<template>
  <div class="flex flex-col items-center max-w-[709px] rounded-2xl border-t border-l-[1.5px] border-r-[0.5px] border-b-2 border-solid border-neutral-700 pb-5">
    <section class="flex flex-col md:flex-row justify-between items-stretch self-stretch gap-2 p-6 md:px-5">
      <div class="flex justify-between items-stretch bg-neutral-500 bg-opacity-10 px-6 py-2.5 rounded-lg md:px-5">
        <h2 class="text-lg font-black leading-5 text-neutral-700">motorcycle</h2>
        <h2 class="text-lg font-bold leading-5 text-neutral-700">Delivery</h2>
      </div>
      <div class="flex justify-between items-stretch px-6 py-2.5 md:px-5">
        <h2 class="text-lg font-black leading-5 text-neutral-700">shopping-bag</h2>
        <h2 class="text-lg font-bold leading-5 text-neutral-700">Pickup</h2>
      </div>
    </section>
    <!-- TODO mettere l'immagine delivery e pickup-->
    <img
        src=""
        alt=""
        class="w-full object-contain object-center rounded-lg overflow-hidden border-neutral-700 border-4"
        loading="lazy"
    />
    <section class="flex flex-col md:flex-row items-center gap-4 mt-6 md:px-5">
      <div class="flex items-center justify-between bg-neutral-700 bg-opacity-10 px-4 py-2 rounded-lg md:pr-5">
        <div class="text-2xl font-black leading-8 text-neutral-500 whitespace-nowrap">METTERE ICONA MAPPA</div>
        <div class="grow self-center text-lg leading-6 text-neutral-700 my-auto whitespace-nowrap">Enter Your Address</div>
        <!-- TODO rendere input box -->
      </div>
      <div class="flex items-center justify-between self-stretch px-12 py-5 rounded-lg md:px-5">
        <button class="text-sm font-black leading-4 text-white my-auto">SEARCH</button>
      </div>
    </section>
  </div>
</template>


<script>
export default {
  name: 'ClientHomeBodyComponent',
}
</script>

<style>
</style>
