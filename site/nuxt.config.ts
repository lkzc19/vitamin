// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-04-03',
  devtools: { enabled: true },
  modules: ['@nuxt/ui', '@nuxt/scripts'],

  scripts: {
    registry: {
      googleAnalytics: {
        id: process.env.GA_TOKEN!,
      },
      clarity: {
        id: process.env.CLARITY_TOKEN!,
      }
    }
  }
})
