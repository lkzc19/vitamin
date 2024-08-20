<script setup lang="ts">
const searchSwitch = useState('search-switch', () => ref(false))
</script>

<template>
  <div class="search-container" @click="searchSwitch = true">
    <UIcon name="heroicons:magnifying-glass" class="mr-1 text-xl" />
    <span>搜索目录和文件</span>
  </div>
</template>

<style scoped>
.search-container {
  @apply flex items-center
  bg-white dark:bg-slate-900 dark:hover:bg-slate-800
  border border-gray-200 hover:border-gray-300 dark:border-gray-800 rounded
  cursor-pointer
  px-3 py-1
  text-gray-400
}
</style>
