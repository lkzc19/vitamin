<script setup lang="ts">
const route = useRoute()
</script>

<template>
  <NuxtLayout name="default">
    <template>
      <div class="grid grid-cols-6 bg-gray-400">
        <div class="row-span-3 bg-slate-500">
          01
        </div>
        <div>
          02
        </div>
        <div>
          03
        </div>
        <div class="row-span-2 bg-zinc-500">
          04
        </div>
        <div class="row-span-4 bg-stone-500">
          05
        </div>
        <div>
          06
        </div>
        <div class="row-span-2 col-span-2 bg-gray-500">
          08
        </div>
        <div class="row-span-2 bg-red-500">
          <h6>12</h6>
          <h6>12</h6>
        </div>
        <div>
          16
        </div>
        <div>
          19
        </div>
        <div>
          20
        </div>
        <div class="col-span-2 bg-orange-500">
          21
        </div>
        <div>
          24
        </div>
      </div>
    </template>
  </NuxtLayout>
</template>
