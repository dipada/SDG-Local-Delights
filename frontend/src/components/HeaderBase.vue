<template>
  <header class="bg-primary">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1 justify-start">
        <a href="#" class="-m-1.5 p-1.5 ">
          <sdg-logo @click="navigateHome"/>
        </a>
      </div>
      <slot name="nav"/>
    </nav>
  </header>
</template>

<script>
import { ref } from 'vue'
import {
  Dialog,
  DialogPanel,
} from '@headlessui/vue'
import {
  Bars3Icon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'
import { ChevronDownIcon, PhoneIcon, PlayCircleIcon } from '@heroicons/vue/20/solid'
import SdgLogo from "@/components/icons/sdgLogo.vue";
const mobileMenuOpen = ref(false)

export default {
  components: {SdgLogo},
  methods: {
    navigateHome() {
      this.$router.push({name: 'home'});
    }
  },
}
</script>