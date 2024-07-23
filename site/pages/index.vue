<script setup lang="ts">

</script>

<template>
  <header>vitamin</header>
  <main>
    <a href="https://www.baidu.com" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://www.baidu.com" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://www.baidu.com" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://www.baidu.com" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
  </main>
  <footer>footer</footer>
</template>

<style scoped>
body {
  @apply relative bg-amber-500 h-screen
}

header {
  @apply absolute top-0 h-10 w-full bg-gray-500 z-50
}

main {
  @apply flex h-screen
}

.item-container {
  @apply basis-1/4 content-center justify-center overflow-hidden
}

.item-img {
  @apply h-screen w-full object-cover transition ease-in-out duration-500 hover:scale-125
}

footer {
  @apply absolute bottom-0 h-10 w-full bg-gray-500
}
</style>
