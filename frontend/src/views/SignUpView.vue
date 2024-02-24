<template>
  <HeaderBase/>

  <div class="lg:m-10 text-black">
    <form @submit.prevent="register"
          class="relative border border-gray-100 space-y-3 max-w-screen-md mx-auto rounded-md bg-white p-6 shadow-xl lg:p-10">
      <h1 class="mb-6 text-xl font-semibold lg:text-2xl">Register</h1>

      <div class="grid gap-3 md:grid-cols-2">
        <div>
          <label> First Name </label>
          <input v-model="formData.firstName" type="text" placeholder="Your Name" required
                 class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        </div>
        <div>
          <label> Last Name </label>
          <input v-model="formData.lastName" type="text" placeholder="Last Name" required
                 class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        </div>
      </div>

      <div>
        <label> Email Address </label>
        <input v-model="formData.email" type="email" placeholder="Info@example.com" required
               class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
      </div>

      <div>
        <label> Shipping address </label>
        <input @input="searchAddress" v-model="formData.shippingAddress" type="text" placeholder="Via roma 1" required
               class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        <div v-if="indirizzi.length" class="relative w-full mt-1 text-secondary">
          <ul class="bg-white border border-gray-300 rounded-lg text-sm">
            <li v-for="(address, index) in indirizzi" :key="index"
                @click="selectAddress(address)"
                class="p-2 hover:bg-gray-100 cursor-pointer">
              {{ address.display_name }}
            </li>
          </ul>
        </div>
      </div>

      <div>
        <label> Password </label>
        <input v-model="formData.password" type="password" placeholder="******" required minlength="8"
               class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
      </div>

      <div class="grid gap-3 lg:grid-cols-2">
        <div>
          <label> Phone: <span class="text-sm text-gray-400">(optional)</span> </label>
          <input v-model="formData.phoneNumber" @input="filterPhoneInput" required type="tel" placeholder="333 1231234"
                 class="mt-2 h-12 w-full rounded-md bg-gray-100 px-3"/>
        </div>
      </div>

      <div>
        <button type="submit" class="mt-5 w-full rounded-md bg-secondary p-2 text-center font-semibold text-white">
          Register now
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import HeaderBase from "@/components/HeaderBase.vue";
import axios from "axios";

const defaultAvatar = "https://innostudio.de/fileuploader/images/default-avatar.png";
export default {
  components: {HeaderBase},

  data() {
    return {
      formData: {
        firstName: "",
        lastName: "",
        email: "",
        shippingAddress: "",
        password: "",
        phoneNumber: "", // optional
        picture: defaultAvatar,
      },
      indirizzi: [],
      query: '',
    };
  },

  methods: {
    selectAddress(address) {
      this.formData.shippingAddress = address.display_name;
      this.indirizzi = [];
    },

    searchAddress() {
      clearTimeout(this.searchTimeout);
      this.searchTimeout = setTimeout(() => {
        if (this.formData.shippingAddress.length > 2) {
          const url = `https://nominatim.openstreetmap.org/search?format=json&limit=5&q=${encodeURIComponent(this.formData.shippingAddress)}`;
          axios.get(url)
              .then(response => {
                this.indirizzi = response.data;
              })
              .catch(error => console.error('Errore nella ricerca degli indirizzi:', error));
        } else {
          this.indirizzi = [];
        }
      }, 500);
    },


    register() {
      if (!this.validateData()) {
        alert("Check the form for errors.");
        return;
      }

      console.log(JSON.stringify(this.formData));

      axios.post("http://localhost:30080/auth/signup", this.formData)
          .then((response) => {
            console.log(response);
            alert("User registered successfully, you will be redirected to the login page.");
            this.$router.push("/login");
          })
          .catch((error) => {
            console.log(error);
            alert("Error occurred while registering user" + error.response.data);
          });
    },

    filterPhoneInput(event) {
      const value = event.target.value;
      this.formData.phoneNumber = value.replace(/[^0-9]/g, '');
    },

    validateData() {
      // email validation
      const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
      if (!emailRegex.test(this.formData.email)) {
        alert("Insert a valid email address");
        return false;
      }
      return true;
    },
  },
};
</script>
