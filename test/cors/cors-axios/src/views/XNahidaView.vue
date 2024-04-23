<script setup>
import {onMounted, ref} from "vue";
import {foo1} from "@/api/cors.js"

const el = ref("")

onMounted(async () => {
  const r = await foo1({name: "携带请求头 X-Nahida"});
  el.value = r.data
})
</script>

<template>
  <div class="about">
    <h1>携带请求头 X-Nahida</h1>
    <p>{{ el }}</p>
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
