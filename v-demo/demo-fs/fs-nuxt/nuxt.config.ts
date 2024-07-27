// https://nuxt.com/docs/api/configuration/nuxt-config
const isDev = process.env.NODE_ENV === 'development'

export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',

  devServer: {
    port: 3001
  },

  runtimeConfig: {
    public: {
      baseURL: isDev ? 'http://localhost:3000' : 'https://drinkice.xyz/sl/api',
    },
  },

  devtools: { enabled: true },
  modules: ['@nuxt/ui']
})