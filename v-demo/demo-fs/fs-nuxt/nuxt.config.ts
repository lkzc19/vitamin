// https://nuxt.com/docs/api/configuration/nuxt-config
const isDev = process.env.NODE_ENV === 'development'

export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',

  devServer: {
    port: 3001
  },

  runtimeConfig: {
    public: {
      // baseUrl: isDev ? 'http://localhost:3000' : 'https://fs.drinkice.xyz/api',
      baseUrl: 'https://fs.drinkice.xyz/api',
    }
  },

  devtools: { enabled: true },
  modules: ['@nuxt/ui']
})
