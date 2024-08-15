<script setup lang="ts">

import Search from "~/components/Search.vue";
import Upload from "~/components/Upload.vue";

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
      <Upload />
      <Search />
      <FileTable />
    </div>
  </main>
</template>

<style scoped>

.center {
  @apply font-mono w-1/2 mx-auto
}

.file-table-container {
  @apply flex-auto
}
</style>
