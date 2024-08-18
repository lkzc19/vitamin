<script setup lang="ts">

const tabs = [{
  label: '简单上传',
  icon: 'heroicons:link-slash-16-solid',
}, {
  label: '分块上传',
  icon: 'heroicons:link-slash-16-solid',
}, {
  label: '断点续传',
  icon: 'heroicons:link-slash-16-solid',
}]

const fileInput = ref<HTMLInputElement>()
const currentIndex = ref(1)

const openFileSelector = () => {
  fileInput.value?.click()
}

const handleUpload = () => {
}
</script>

<template>
  <div class="flex flex-row">
    <input type="file" ref="fileInput" class="hidden">
    <ol class="bg-slate-900 border border-gray-200 dark:border-gray-800 rounded-sm p-1 items-center text-center">
      <li
          v-for="(it, index) in tabs"
          :key="index"
          @click="currentIndex = index"
          :class="{ active: index == currentIndex }"
          class="flex items-center px-10 py-1 rounded-sm"
      >
        <UIcon name="heroicons:link-16-solid" class="mr-2" v-if="index == currentIndex" />
        <UIcon :name="it.icon" class="mr-2" v-else />
        {{ it.label }}
      </li>
    </ol>

    <div class="bg-slate-900 border-2 border-dashed border-gray-200 dark:border-gray-800 grow mx-2 flex justify-center items-center cursor-pointer"
         @click="openFileSelector"
    >
      <UIcon name="heroicons:plus-16-solid" />
    </div>

    <div class="bg-slate-900 border border-gray-200 dark:border-gray-800 flex justify-center content-center">
      <UButton icon="heroicons:arrow-up-tray-16-solid" variant="none" color="black" class="px-2" :to="handleUpload" />
    </div>
  </div>
</template>

<style scoped>
.active {
  @apply bg-slate-800;
}
</style>
