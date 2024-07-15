<script setup lang="ts">
import type {Link} from "~/types";

const origURL = ref('http://www.baidu.com')
let link = ref<Link | null>(null)
let qrCode = ref('')
let copyIcon = ref('i-heroicons-square-2-stack')
const toast = useToast()

const getLink = async () => {
  if (!origURL.value.startsWith('http://') && !origURL.value.startsWith('https://')) {
    toast.add({ title: '请输入 http:// 或 https:// 开头的网址', color: 'red' })
    return;
  }

  const { data } = await useFetch(useRuntimeConfig().public.baseURL, {
    method: 'GET',
    query: { link: origURL.value },
  })
  link.value = data.value as Link
  qrCode.value = "data:image/jpeg;base64," + link.value.QRCode
  copyIcon.value = "i-heroicons-square-2-stack"
}

const copyText = async () => {
  await navigator.clipboard.writeText(<string>link.value?.shortURL);
  copyIcon.value = "i-heroicons-check-badge"
  toast.add({ title: '链接已复制到剪切板' })
};
</script>

<template>
  <UContainer class="min-h-screen flex-col place-content-center font-mono">

    <UButtonGroup size="xl" orientation="horizontal" class="flex">
      <UInput color="gray" variant="outline" placeholder="请输入 http:// 或 https:// 开头的网址" class="grow" v-model="origURL"/>
      <UButton icon="i-heroicons-paper-airplane" color="gray" @click="getLink"/>
    </UButtonGroup>

    <div class="mt-5 p-5 border border-gray-700 rounded-sm bg-gray-800" v-if="link != null">
      <div class="flex">
        <div class="mr-2">
          <img :src="qrCode" alt="qrcode"/>
        </div>
        <div class="flex-col ml-5 mt-3">
          <div class="align-baseline text-lg">
            <span>短链接: </span>
            <ULink
                :to="link?.shortURL"
                active-class="text-primary"
                inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
            > {{ link?.shortURL }} </ULink>
            <UButton :icon="copyIcon" color="gray" variant="link" class="ml-3 pt-1 cursor-pointer align-middle" @click="copyText" />
<!--            <span class="text-gray-400"> | </span>-->
          </div>
          <div class="text-gray-400 text-sm mt-2">
            <span>原始链接: </span>
            <span>{{ link?.origURL }}</span>
          </div>
        </div>
      </div>
      <div class="mt-5">注意: 该网站仅是一个demo。 链接仅30分钟内有效, 且未持久化, 服务器重启后数据就会丢失。</div>
    </div>
  </UContainer>
</template>

<style>
body {
  @apply antialiased font-sans text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900;
}
</style>