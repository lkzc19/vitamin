export default defineNuxtPlugin(() => {
    const config = useRuntimeConfig()

    const $fsFetch = $fetch.create({
        baseURL: config.baseUrl as string,
        onRequest({ request, options, error }) {
            options.headers = options.headers || {}
        },
        onResponse ({ response }) {
        },
        onResponseError({ response }) {
        }
    })

    // Expose to useNuxtApp().$fsFetch
    return {
        provide: {
            $fsFetch
        }
    }
})
