<script setup lang="ts">

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseUrl


</script>

<template>
  <header>
    <div class="center h-full">
      <h1 class="text-6xl">
        <a href="/">vitamin</a>
      </h1>
    </div>
  </header>
  <main class="mt-5">
    <div class="center">
      <div class="flex flex-row">
        <div class="file-table-container">
          <FileTable />
        </div>
        <div class="basis-1/4 bg-gray-600">
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
  @apply font-mono w-1/2 mx-auto
}

.file-table-container {
  @apply basis-3/4 mr-2
}
</style>
