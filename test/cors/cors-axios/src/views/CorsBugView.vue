<script setup>
import {onMounted, ref} from "vue";
import { ping } from "@/api/cors.js"

const el = ref("")

onMounted(async () => {
  const pong = await ping();
  el.value = pong.data
})
</script>

<template>
  <div class="about">
    <h1>ping</h1>
    <p>{{ el }}</p>
  </div>
</template>

<style>
@media (min-width: 1024px) {
  .about {
    min-height: 100vh;
    display: flex;
    align-items: center;
  }
}
</style>
