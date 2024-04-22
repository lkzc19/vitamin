<script setup>
import {onMounted, ref} from "vue";
import { foo } from "@/api/cors.js"

const el = ref("")

onMounted(async () => {
  const bar = await foo({name: "胡桃"});
  el.value = bar.data
})
</script>

<template>
  <div class="about">
    <h1>foo</h1>

    <span>{{ el }}</span>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    flex-direction: column; /* 设置为垂直方向排列 */
    align-items: center;
    justify-content: center; /* 垂直居中对齐 */
  }
}
</style>
