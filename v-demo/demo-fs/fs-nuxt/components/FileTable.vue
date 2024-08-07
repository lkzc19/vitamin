<script setup lang="ts">
import type {Page, FileMeta} from "~/types";

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseUrl

const columns = [{
  key: 'id',
  label: 'id'
}, {
  key: 'name',
  label: '名称'
}, {
  key: 'fileSize',
  label: '文件大小'
}, {
  key: 'action'
}]

const data = [{
  id: 1,
  name: 'Lindsay Walton',
  fileSize: 'Front-end Developer',
}, {
  id: 2,
  name: 'Courtney Henry',
  fileSize: 'Designer',
}, {
  id: 3,
  name: 'Tom Cook',
  fileSize: 'Director of Product',
}]

const items = (row: any) => [
  [{
    label: 'Edit',
    icon: 'i-heroicons-pencil-square-20-solid',
    click: () => console.log('Edit', row.id)
  }, {
    label: 'Duplicate',
    icon: 'i-heroicons-document-duplicate-20-solid'
  }], [{
    label: 'Archive',
    icon: 'i-heroicons-archive-box-20-solid'
  }, {
    label: 'Move',
    icon: 'i-heroicons-arrow-right-circle-20-solid'
  }], [{
    label: 'Delete',
    icon: 'i-heroicons-trash-20-solid'
  }]
]

// const pageC = ref(1)
// const pageS = ref(10)
// const pageT = ref(0)
// const fileMetaPage = ref<Page<FileMeta> | null>(null)
// const fileMetaList = ref<FileMeta[]>(Array())
//
// const { data } = await useFetch(baseURL + "/file.list", {
//   method: 'GET',
//   params: {
//     'path': route.path,
//     'pageC': pageC.value,
//     'pageS': pageS.value
//   }
// })
//
// fileMetaPage.value = data.value as Page<FileMeta>
// pageC.value = fileMetaPage.value.pageC
// pageS.value = fileMetaPage.value.pageS
// pageT.value = fileMetaPage.value.pageT
// fileMetaList.value = fileMetaPage.value.items
</script>

<template>
  <UTable :columns="columns" :rows="data">
    <template #actions-data="{ row }">
      <UDropdown :items="items(row)">
        <UButton color="gray" variant="ghost" icon="i-heroicons-ellipsis-horizontal-20-solid" />
      </UDropdown>
    </template>
  </UTable>
</template>

<style scoped>

</style>
