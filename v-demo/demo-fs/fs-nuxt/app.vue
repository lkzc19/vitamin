<script setup>
useSeoMeta({
  title: '饮冰室的文件柜',
  description: '一个文件服务demo'
})
</script>

<template>
  <div>
    <NuxtRouteAnnouncer />
    <NuxtPage />
    <UNotifications />
  </div>
</template>

<style>
body {
  @apply h-full
  bg-gray-100 dark:bg-stone-900
}
</style>
