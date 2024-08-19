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
const currentIndex = ref(0)

const openFileSelector = () => {
  fileInput.value?.click()
}

const handleUpload = () => {
}
</script>

<template>
  <div class="flex flex-row">
    <input type="file" ref="fileInput" class="hidden">
    <ol class="upload-ol">
      <li
          v-for="(it, index) in tabs"
          :key="index"
          @click="currentIndex = index"
          :class="{ active: index == currentIndex }"
          class="upload-li"
      >
        <UIcon name="heroicons:link-16-solid" class="mr-2" v-if="index == currentIndex" />
        <UIcon :name="it.icon" class="mr-2" v-else />
        {{ it.label }}
      </li>
    </ol>

    <div class="upload-div"
         @click="openFileSelector"
    >
      <UIcon name="heroicons:plus-16-solid"  />
    </div>

    <div class="upload-button">
      <UButton icon="heroicons:arrow-up-tray-16-solid" variant="none" color="black" class="px-2" :to="handleUpload" />
    </div>
  </div>
</template>

<style scoped>
.active {
  @apply bg-gray-100 dark:bg-slate-800;
}

.upload-ol {
  @apply bg-white dark:bg-slate-900
  border border-gray-200 dark:border-gray-800 rounded-sm
  p-1
  items-center text-center
}

.upload-li {
  @apply flex items-center
  px-10 py-1
  rounded-sm
  cursor-pointer
}

.upload-div {
  @apply bg-white dark:bg-slate-900
  border-2 border-dashed border-gray-200 dark:border-gray-800 rounded-sm
  grow
  mx-2
  flex justify-center items-center
  cursor-pointer
}

.upload-button {
  @apply bg-white dark:bg-slate-900
  border border-gray-200 dark:border-gray-800 rounded-sm
  flex justify-center content-center
}
</style>
