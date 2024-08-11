<script setup lang="ts">
import type {FileMeta} from "~/types";
import {parentPath} from "~/utils";

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseUrl

const pageC = ref(1)
const pageS = ref(10)
const pageT = ref(0)

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

const fileMetaList = ref<FileMeta[]>(Array())

const { data } = await useFetch(baseURL + "/file.list", {
  method: 'GET',
  params: { 'path': route.path + (route.path.endsWith("/") ? "" : "/") }
})

fileMetaList.value = data.value as FileMeta[]
fileMetaList.value.map((it: FileMeta, index: number) => it.id = index + 1)
pageT.value = fileMetaList.value.length

const path = route.path + (route.path.endsWith("/") ? "" : "/")

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
</script>

<template>
  <UCard
      class="w-full"
      :ui="{
        base: '',
        ring: 'ring-1 ring-gray-200 dark:ring-gray-800',
        rounded: '',
        divide: 'divide-y divide-gray-200 dark:divide-gray-700',
        header: { padding: 'px-4 py-5' },
        body: { padding: '', base: 'divide-y divide-gray-200 dark:divide-gray-700' },
        footer: { padding: 'p-4' }
      }"
  >

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
                 :to="to(row.name)"
        />
        <UButton v-else
                 icon="heroicons:arrow-down-16-solid"
                 variant="outline"
                 :to="download(row.name)"
        />
      </template>
    </UTable>

    <!-- Number of rows & Pagination -->
    <template #footer>
      <div class="flex flex-wrap justify-between items-center">
        <div class="flex items-center gap-1.5">
          <span class="text-sm leading-5">每页多少行:</span>
          <USelect
              v-model="pageS"
              :options="[3, 5, 10, 20, 30, 50]"
              class="me-2 w-20"
              size="xs"
          />
        </div>

        <UPagination
            v-model="pageC"
            :page-count="pageS"
            :total="(pageT / pageS) + ((pageT % pageS) == 0 ? 0 : 1)"
            :ui="{
            wrapper: 'flex items-center gap-1',
            rounded: '!rounded-full min-w-[32px] justify-center',
            default: {
              activeButton: {
                variant: 'outline'
              }
            }
          }"
        />
      </div>
    </template>
  </UCard>
</template>

<style scoped>

</style>
