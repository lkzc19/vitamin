<script setup lang="ts">

import {addSlant, getFilename} from "~/utils/pathUtils";

const route = useRoute()
const refreshTable = useState<number>('refresh-table')
const toast = useToast()

const baseURL = useRuntimeConfig().public.baseUrl

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
const fileInputKey = ref(1)
const filename = useState<string>('upload-filename', () => "")
const file = useState<File>('upload-file')
const currentIndex = ref(0)

const openFileSelector = () => {
  fileInput.value?.click()
}

const handleChange = () => {
  if (fileInput.value) {
    filename.value = getFilename(fileInput.value.value)
    if (fileInput.value.files) {
      file.value = fileInput.value.files[0] as File
    }
  }
}

const handleUpload = async () => {
  if (filename.value == '') {
    toast.add({
      title: '上传文件失败',
      description: '文件为空',
      color: 'red',
    })
    return
  }

  if (currentIndex.value == 0) {
    const formData = new FormData()
    formData.append("file", file.value)
    formData.append("path", addSlant(route.path))
    console.log(formData)
    const response = await $fetch(baseURL + "/upload.simple", {
      method: 'POST',
      body: formData
    })
  } else if (currentIndex.value == 1) {
    toast.add({
      title: 'TODO',
      description: '分块上传还未实现',
      color: 'red',
    })
  } else {
    toast.add({
      title: 'TODO',
      description: '断点续传还未实现',
      color: 'red',
    })
  }
  refreshTable.value++
  clear()
}

const clear = () => {
  filename.value = ''
  fileInputKey.value++
}
</script>

<template>
  <div class="flex flex-row">
    <input type="file" ref="fileInput" @change="handleChange" :key="fileInputKey" class="hidden"/>
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

    <div class="upload-div" @click="openFileSelector">
      <UIcon v-if="filename == ''" name="heroicons:plus-16-solid" />
      <span v-else>{{ filename }}</span>
    </div>

    <div class="upload-button">
      <UButton icon="heroicons:arrow-up-tray-16-solid" variant="link" color="black" class="px-2" @click="handleUpload" />
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
  rounded-md
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
