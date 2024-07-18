<script setup lang="ts">
import type {Link} from "~/types";

// https://www.baidu.com
const origURL = ref('')
let link = ref<Link | null>(null)
let qrCode = ref('')
let copyIcon = ref('i-heroicons-square-2-stack')
const toast = useToast()

const getLink = async () => {
  if (!origURL.value.startsWith('http://') && !origURL.value.startsWith('https://')) {
    toast.add({ title: '请输入 http:// 或 https:// 开头的网址', color: 'red' })
    return;
  }

  const { data, status, error } = await useFetch(useRuntimeConfig().public.baseURL, {
    method: 'GET',
    query: { link: origURL.value },
  })
  const message = error.value?.data.message
  if (status.value === 'error' && message != null) {
    toast.add({ title: message, color: 'blue' })
    return
  }
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
  <UContainer class="min-h-screen flex-col place-content-center font-mono min-w-72">

    <h1 class="text-gray-900 dark:text-gray-100 text-center text-5xl">
      一 个 短 链 demo
    </h1>

    <div class="flex mt-10 px-5 py-3 border border-zinc-200 dark:border-gray-700 bg-zinc-100 dark:bg-gray-800 rounded-sm">
      <input type="text"
             placeholder="请输入 http:// 或 https:// 开头的网址"
             class="grow focus:border-transparent bg-zinc-100 dark:bg-gray-800 focus:outline-none pr-5 text-lg"
             v-model="origURL"
      />
      <UButton color="gray" @click="getLink" class="text-lg">
        生成短链
        <template #trailing>
          <UIcon name="i-heroicons-paper-airplane" class="w-5 h-5" />
        </template>
      </UButton>
    </div>

    <div class="mt-5 p-5 border border-zinc-200 dark:border-gray-700 bg-zinc-100 dark:bg-gray-800 rounded-sm" v-if="link != null">
      <div class="flex">
        <div class="mr-2">
          <img :src="qrCode" alt="qrcode" class="size-28" />
        </div>
        <div class="flex-col ml-5 mt-3">
          <div class="align-baseline text-lg">
            <span>短链接: </span>
            <ULink
                :to="link?.shortURL"
                active-class="text-primary"
                inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
            > {{ link?.shortURL }} </ULink>
            <UButton :icon="copyIcon" color="gray" variant="link" class="ml-2 pt-1 cursor-pointer align-middle" @click="copyText" />
<!--            <span class="text-gray-400"> | </span>-->
          </div>
          <div class="text-gray-500 dark:text-gray-400 text-sm mt-2 w-96 text-nowrap text-ellipsis overflow-hidden">
            原始链接: {{ link?.origURL }}
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