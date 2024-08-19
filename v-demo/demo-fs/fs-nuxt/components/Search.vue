<script setup lang="ts">
import type {FileMeta} from "~/types";

const toast = useToast()
const baseURL = useRuntimeConfig().public.baseUrl

const v = ref("")
const fileList = ref<FileMeta[]>([])
const isFocused = ref(false)
const handleFocus = () => isFocused.value = true
const handleBlur = () => isFocused.value = false
const handleInput = async () => {
  if (v.value.length == 0) {
    fileList.value = []
    return
  }

  const _fileList = await $fetch(baseURL + "/file.search", {
    method: 'GET',
    query: {
      "keyword": v.value,
    }
  })
  fileList.value = _fileList as FileMeta[]
  console.log(_fileList)
}
</script>

<template>
  <div>
    <div class="search-container">
      <UIcon name="heroicons:magnifying-glass" class="mr-2" />
      <input type="text"
             placeholder="搜索..."
             class="search-container-input"
             v-model="v"
             @focus="handleFocus"
             @blur="handleBlur"
             @input="handleInput"
      />
    </div>
    <ul class="absolute bg-white dark:bg-slate-900 border border-t-0 border-gray-200 dark:border-gray-800 px-6 py-1 w-1/2 z-50"
        v-show=" (isFocused && fileList.length > 0)"
    >
      <li v-for="it in fileList" class="py-1">
        <ULink
            v-if="it.isDir"
            active-class="text-primary"
            inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
        >
          <UIcon name="heroicons:folder" />
          {{ it.fullPath }}
        </ULink>
        <ULink
            v-else
            active-class="text-primary"
            inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
        >
          <UIcon name="heroicons:document" />
          {{ it.fullPath }}
        </ULink>
      </li>
    </ul>
  </div>
</template>

<style scoped>
.search-container {
  @apply relative
  flex items-center
  bg-white dark:bg-slate-900
  border border-gray-200 dark:border-gray-800 rounded-sm
  py-3 px-6
}

.search-container-input {
  @apply focus:border-transparent focus:outline-none grow
  dark:bg-slate-900
  py-2
}

</style>
