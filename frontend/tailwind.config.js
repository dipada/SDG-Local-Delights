/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
    "./node_modules/flowbite/**/*.js",
  ],
  theme: {
    extend: {
      colors: {
        'primary': '#DAD7CD',
        'secondary': '#588157',
      },
    },
  },
  plugins: [
      require('flowbite/plugin'),
      require('@tailwindcss/forms')
  ],
}

