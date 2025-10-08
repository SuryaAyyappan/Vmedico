/** @type {import('tailwindcss').Config} */
export default {
  content: [
    './index.html',
    './src/**/*.{vue,js,ts,jsx,tsx}',
  ],
  theme: {
    extend: {
      colors: {},
      fontFamily: {},
      boxShadow: {
        premium: '0 10px 30px rgba(17,42,121,0.15)',
      },
    },
  },
  plugins: [],
}


