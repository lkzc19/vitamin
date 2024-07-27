<script setup lang="ts">
import type {FileMeta} from "~/types";

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseURL
const ds = ref<FileMeta[] | null>(null)

onMounted(async () => {
  const {data, status, error} = await useFetch(baseURL + "/ds", {
    method: 'GET',
  })

  ds.value = data.value as FileMeta[]
  console.log(data)
  console.log(status)
  console.log(error)
})

const getDS = async () => {
  const {data, status, error} = await useFetch(baseURL + "/ds", {
    method: 'GET',
  })

  ds.value = data.value as FileMeta[]
  console.log(data)
  console.log(status)
  console.log(error)
}
// console.log(ds.value)
</script>

<template>
  <header>
    <div class="center h-full">
      vitamin
    </div>
  </header>
  <main>
    <div class="center">
      <div class="flex flex-row">
        <div class="basis-3/4 mr-2">
          <div></div>
        </div>
        <div class="basis-1/4">
          <div @click="getDS">
            搜索框
          </div>
          <div>
            上传文件处
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
.center {
  @apply w-1/2 mx-auto bg-gray-800
}

header {
  @apply h-8
}

main {

}
</style>
