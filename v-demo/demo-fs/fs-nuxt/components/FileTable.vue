<script setup lang="ts">
import type {FileMeta, Info} from "~/types";
import {toNumber} from "~/utils";
import {addSlant, parentPath} from "~/utils/pathUtils";

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseUrl
const toast = useToast()
const refreshTable = useState<number>('refresh-table', () => 42)

const p = ref(toNumber(route.query.p))

const columns = [{
  key: 'id',
  label: '#',
  sortable: true
}, {
  key: 'name',
  label: '名称'
}, {
  key: 'size',
  label: '文件大小'
}, {
  key: 'action',
  label: '操作'
}]

const { data: _info } = await useFetch(baseURL + "/info", {
  method: 'GET',
})
const info = _info.value as Info

const path = addSlant(route.path)
let allFile: FileMeta[] = []
const fileMetaList = ref<FileMeta[]>()
const size = 10
const startIndex = () => (p.value - 1) * size
const endIndex = () => {
  const si = startIndex()
  const ei = si + 10
  return  ei >= allFile.length ? allFile.length : ei
}
const updateFileList = async () => {
  const {data: _allFile} = await useFetch(baseURL + "/file.list", {
    method: 'GET',
    params: {'path': path}
  })
  allFile = (_allFile.value as FileMeta[]).map((it: FileMeta, index: number) => {
    it.id = index + 1;
    return it
  })
  fileMetaList.value = allFile.slice(startIndex(), endIndex())
}

updateFileList()

const to = (dir: string) => {
  if (dir == "..") {
    return parentPath(path)
  } else {
    return path + dir
  }
}
const download = (name: string) => baseURL + "/download?fullPath=" + path + name
const addUnit = (size: number) => {
  if (size < 1024) {
    return size + " B"
  } else if (1024 < size && size < 1024 **2) {
    return (size / 1024.0).toFixed(2) + " KB"
  } else if (1024 ** 2 < size && size < 1024 ** 3) {
    return (size / 1024.0 ** 2).toFixed(2) + " MB"
  } else {
    return (size / 1024.0 ** 3).toFixed(2) + " GB"
  }
}

watch(p, (newVal, _) => {
  fileMetaList.value = allFile.slice(startIndex(), endIndex())
  navigateTo({
    query: { p: newVal },
    // Hash is specified here to prevent the page from scrolling to the top
    hash: route.hash,
  })
})
watch(refreshTable, () => {
  updateFileList()
})

const newDir = ref("")
const mkdir = async () => {
  if (newDir.value.length == 0) {
    toast.add({
      title: '创建目录失败',
      description: '目录名不可为空字符串',
      color: 'red',
    })
    return
  }
  if (newDir.value.length > 16) {
    toast.add({
      title: '创建目录失败',
      description: '目录名长度不可超过16',
      color: 'red',
    })
    return
  }
  const _dir = await $fetch(baseURL + "/dir", {
    method: 'PUT',
    query: {
      "path": path,
      "name": newDir.value
    }
  })
  newDir.value = ""
  refreshTable.value++
}
</script>

<template>
  <UCard
      class="w-full"
      :ui="{
        base: '',
        ring: 'ring-1 ring-gray-200 dark:ring-gray-800',
        rounded: '',
        divide: 'divide-y divide-gray-200 dark:divide-gray-700',
        header: { padding: 'px-4 py-5', rounded: 'rounded-sm' },
        body: { padding: '', base: 'divide-y divide-gray-200 dark:divide-gray-700' },
        footer: { padding: 'p-4', rounded: 'rounded-sm' }
      }"
  >

    <template #header>
      <div class="flex flex-wrap justify-between items-center">
        <div class="flex items-center">
          <UButton icon="i-heroicons-arrow-small-up-20-solid" variant="outline" color="black" :to="to('..')">..</UButton>
          <div class="ml-5">当前路径: [ {{ route.path }} ]</div>
        </div>

        <UPagination
            v-model="p"
            :page-count="size"
            :total="allFile.length"
            :active-button="{color: 'black'}"
        />
      </div>
    </template>

    <UTable
        :columns="columns"
        :rows="fileMetaList"
        :empty-state="{ icon: 'i-heroicons-circle-stack-20-solid', label: '此 地 无 银' }"
    >
      <template #name-data="{ row }">
        <ULink
            v-if="row.isDir"
            :to="to(row.name)"
            active-class="text-primary"
            inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
        >
          <UIcon name="heroicons:folder" />
          {{ row.name }}
        </ULink>
        <ULink
            v-else
            :to="download(row.name)"
            active-class="text-primary"
            inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
        >
          <UIcon name="heroicons:document" />
          {{ row.name }}
        </ULink>
      </template>

      <template #size-data="{ row }">
        <span>{{row.isDir ? "-" : addUnit(row.size)}}</span>
      </template>

      <template #action-data="{ row }">
        <UButton v-if="row.isDir"
                 icon="heroicons:arrow-right-16-solid"
                 variant="outline"
                 color="black"
                 :to="to(row.name)"
        />
        <UButton v-else
                 icon="heroicons:arrow-down-16-solid"
                 variant="outline"
                 color="black"
                 :to="download(row.name)"
        />
      </template>
    </UTable>

    <template #footer>
      <div class="flex flex-wrap justify-between items-center">
        <span>文件总数: {{ info.fileCount }} (不包含目录)</span>
        <div class="flex">
          <input type="text"
                 placeholder="请输入 新目录名称"
                 class="focus:outline-none border border-r-0 border-black dark:border-white dark:bg-gray-900 rounded-l-md px-2 text-sm"
                 v-model="newDir"
          />
          <UButton icon="heroicons:folder-plus"
                   variant="outline"
                   color="black"
                   :ui="{ rounded: 'rounded-l-sm' }"
                   @click="mkdir"
          >
            创建目录
          </UButton>
        </div>
      </div>
    </template>
  </UCard>
</template>

<style scoped>

</style>
