<template>
  <HeaderBase />

  <div class="lg:m-10 text-black">
    <form @submit.prevent="register" class="relative border border-gray-100 space-y-3 max-w-screen-md mx-auto rounded-md bg-white p-6 shadow-xl lg:p-10">
      <h1 class="mb-6 text-xl font-semibold lg:text-2xl">Register</h1>

      <div class="grid gap-3 md:grid-cols-2">
        <div>
          <label> First Name </label>
          <input v-model="formData.firstName" type="text" placeholder="Your Name" required class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
        </div>
        <div>
          <label> Last Name </label>
          <input v-model="formData.lastName" type="text" placeholder="Last Name" required class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
        </div>
      </div>

      <div>
        <label> Username </label>
        <input v-model="formData.username" type="text" placeholder="Username" required class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
      </div>

      <div>
        <label> Email Address </label>
        <input v-model="formData.email" type="email" placeholder="Info@example.com" required class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
      </div>

      <div>
        <label> Password </label>
        <input v-model="formData.password" type="password" placeholder="******" required minlength="8" class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
      </div>

      <div class="grid gap-3 lg:grid-cols-2">
        <div>
          <label> Phone: <span class="text-sm text-gray-400">(optional)</span> </label>
          <input v-model="formData.phone" @input="filterPhoneInput" type="tel" placeholder="333 1231234" class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3" />
        </div>
      </div>

      <div>
        <button type="submit" class="mt-5 w-full rounded-md bg-secondary p-2 text-center font-semibold text-white">Register now</button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import HeaderBase from "@/components/HeaderBase.vue";

const formData = ref({
  firstName: "",
  lastName: "",
  username: "",
  email: "",
  password: "",
  phone: "",
});

const register = () => {
  if (!validateData()) {
    alert("Check the form for errors.");
    return;
  }

  console.log(JSON.stringify(formData.value));
  // TODO: POST to the server
};

const filterPhoneInput = (event) => {
  const value = event.target.value;
  formData.value.phone = value.replace(/[^0-9]/g, '');
};

const validateData = () => {
  // email validation
  const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
  if (!emailRegex.test(formData.value.email)) {
    alert("Insert a valid email address");
    return false;
  }
  return true;
};

</script>
