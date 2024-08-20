<script setup lang="ts">

import Upload from "~/components/Upload.vue";
import HSearch from "~/components/header/Search.vue";
import SearchModal from "~/components/SearchModal.vue";
</script>

<template>
  <header>
    <div class="center h-full flex justify-between">
      <h1 class="text-6xl">
        <a href="/">vitamin</a>
      </h1>
      <div class="flex items-center">
        <HSearch />
      </div>
    </div>
  </header>
  <main class="mt-5">
    <SearchModal />
    <div class="center">
      <Upload class="mb-5" />
      <FileTable />
    </div>
  </main>
</template>

<style scoped>
.center {
  @apply font-mono w-1/2 mx-auto
}
</style>
