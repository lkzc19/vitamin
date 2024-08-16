<script setup lang="ts">

</script>

<template>
  <header>
    <h1 class="font-2077">
      <a href="/">drinkice</a>
    </h1>
  </header>
  <main>
    <a href="https://sl.drinkice.xyz/" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://fs.drinkice.xyz/" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://sl.drinkice.xyz/" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
    <a href="https://sl.drinkice.xyz/" class="item-container">
      <img src="~/assets/img/pw_02.jpg" alt="pw" class="item-img" />
    </a>
  </main>
  <footer>
    <a href="https://github.com/lkzc19/vitamin" class="font-2077 mb-2">vitamin</a>
    <a href="https://beian.miit.gov.cn/" class="icp">闽ICP备2024063666号</a>
  </footer>
</template>

<style scoped>
body {
  @apply relative h-screen
}

header {
  @apply absolute top-0 w-full z-50 select-none
}

header h1 {
  @apply ml-8 text-black font-bold align-middle text-7xl
}

main {
  @apply flex h-screen
}

.item-container {
  @apply basis-1/4 content-center justify-items-center overflow-hidden
}

.item-img {
  @apply h-screen w-full object-cover
  transition ease-in-out duration-500 hover:scale-125

  before:bg-red-500 before:h-screen before:w-full
}

footer {
  @apply absolute bottom-0 flex flex-col
  pb-2 w-full z-50
  text-black text-center
}

@font-face {
  font-family: 'Cyberpunk';
  src: url('~/assets/font/Cyberpunk-Regular.ttf') format('truetype');
}

.font-2077 {
  font-family: 'Cyberpunk', monospace;
}

.icp {
  @apply font-mono text-xs
}
</style>
