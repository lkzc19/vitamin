<script setup lang="ts">
import type {Page, FileMeta} from "~/types";

const route = useRoute()
const baseURL = useRuntimeConfig().public.baseUrl

const pageC = ref(1)
const pageS = ref(10)
const pageT = ref(0)
const fileMetaPage = ref<Page<FileMeta> | null>(null)
const fileMetaList = ref<FileMeta[]>(Array())

const { data } = await useFetch(baseURL + "/file.list", {
  method: 'GET',
  params: {
    'path': route.path,
    'pageC': pageC.value,
    'pageS': pageS.value
  }
})

fileMetaPage.value = data.value as Page<FileMeta>
pageC.value = fileMetaPage.value.pageC
pageS.value = fileMetaPage.value.pageS
pageT.value = fileMetaPage.value.pageT
fileMetaList.value = fileMetaPage.value.items
</script>

<template>
  <header>
    <div class="center h-full">
      vitamin
    </div>
  </header>
  <main>
    <div class="center">
      <UPagination v-model="pageC" :page-count="pageS" :total="pageT" />
      <div class="flex flex-row">
        <div class="basis-3/4 mr-2">
          <UTable :rows="fileMetaList" />
        </div>
        <div class="basis-1/4">
          <div>
            搜索框
          </div>
          <div>
            上传文件处
          </div>
        </div>
      </div>
    </div>
  </main>
</template>

<style scoped>
.center {
  @apply w-1/2 mx-auto bg-gray-800
}

header {
  @apply h-8
}

main {

}
</style>
