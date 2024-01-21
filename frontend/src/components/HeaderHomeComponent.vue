<template>
  <header class="bg-primary">
    <nav class="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
      <div class="flex lg:flex-1">
        <a href="#" class=" max-w-18 -m-1.5 p-1.5 ">
          <sdg-logo/>
        </a>
      </div>
      <div class="flex lg:hidden">
        <button type="button" class="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5 text-gray-700" @click="mobileMenuOpen = true">
          <Bars3Icon class="h-6 w-6" aria-hidden="true" />
        </button>
      </div>
      <div class="hidden lg:flex lg:flex-1 lg:justify-end">
        <!-- <a href="#" class="text-sm font-semibold leading-6 text-gray-900">Log ian <span aria-hidden="true">&rarr;</span></a> -->

        <router-link to="">
          <button class="max-h-20 min-h-20 py-6 min-w-44 border-2 bg-primary items-center text-green-800 font-bold hover:bg-green-900 hover:text-primary rounded-lg border-secondary">Log in</button>
        </router-link>
        <router-link to="">
          <button class="ml-3 max-h-20 min-h-20 py-6 min-w-44 w-full items-center bg-secondary font-bold text-white hover:bg-green-900 rounded-lg">Sign in</button>
        </router-link>

      </div>
    </nav>
      <SearchZip class="w-full -mb-8 flex justify-center z-10" />

    <Dialog as="div" class="lg:hidden" @close="mobileMenuOpen = false" :open="mobileMenuOpen">
      <div class="fixed inset-0 z-10" />
      <DialogPanel class="bg-primary rounded-b-3xl b fixed inset-y-0 right-0 z-10 w-full overflow-y-auto px-6 py-6 sm:max-w-sm sm:ring-1 sm:ring-gray-900/10 max-h-fit min-w-full">
        <div class="flex items-center justify-between">
          <a href="#" class="-m-1.5 p-1.5">
            <span class="sr-only">Your Company</span>
            <sdg-logo/>
          </a>
          <button type="button" class="-m-2.5 rounded-md p-2.5 text-gray-700" @click="mobileMenuOpen = false">
            <XMarkIcon class="h-6 w-6" aria-hidden="true" />
          </button>
        </div>
        <div class="mt-6">
          <div class="-my-6 divide-y divide-gray-500/10 justify-center items-center">
            <router-link to="">
              <button class="mt-12 mb-4 py-6 w-full items-center bg-primary text-green-800 font-bold hover:bg-green-900 hover:text-primary rounded-lg border-secondary border-4">Log in</button>
            </router-link>
            <router-link to="">
              <button class="mb-10 py-6 w-full items-center bg-secondary font-bold text-white hover:bg-green-900 rounded-lg">Sign in</button>
            </router-link>
          </div>
        </div>
      </DialogPanel>
    </Dialog>
  </header>
</template>

<script setup>
import { ref } from 'vue'
import {
  Dialog,
  DialogPanel,
} from '@headlessui/vue'
import {
  ArrowPathIcon,
  Bars3Icon,
  ChartPieIcon,
  CursorArrowRaysIcon,
  FingerPrintIcon,
  SquaresPlusIcon,
  XMarkIcon,
} from '@heroicons/vue/24/outline'
import { ChevronDownIcon, PhoneIcon, PlayCircleIcon } from '@heroicons/vue/20/solid'
import SdgLogo from "@/components/icons/sdgLogo.vue";
import SearchZip from "@/components/SearchZip.vue";

const products = [
  { name: 'Analytics', description: 'Get a better understanding of your traffic', href: '#', icon: ChartPieIcon },
  { name: 'Engagement', description: 'Speak directly to your customers', href: '#', icon: CursorArrowRaysIcon },
  { name: 'Security', description: 'Your customers’ data will be safe and secure', href: '#', icon: FingerPrintIcon },
  { name: 'Integrations', description: 'Connect with third-party tools', href: '#', icon: SquaresPlusIcon },
  { name: 'Automations', description: 'Build strategic funnels that will convert', href: '#', icon: ArrowPathIcon },
]
const callsToAction = [
  { name: 'Watch demo', href: '#', icon: PlayCircleIcon },
  { name: 'Contact sales', href: '#', icon: PhoneIcon },
]

const mobileMenuOpen = ref(false)
</script>