<script setup>
  import { ping } from "@/api/cors.js"

  async function ping() {
    const pong = await ping();
    console.log(pong)
  }
</script>

<template>
  <div class="about">
    <h1></h1>
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
