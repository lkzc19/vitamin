<script setup lang="ts">

</script>

<template>
  <header>
    <div class="layout">
      <h1>
        vitamin
      </h1>
    </div>

  </header>
  <main class="layout">
    <slot />
  </main>
  <footer>
    <div class="layout">
      footer
    </div>
  </footer>
</template>

<style scoped>
.layout {
  @apply max-w-screen-xl mx-auto font-mono
}

main {
  @apply bg-primary
}

header {
  @apply h-16 bg-gray-800
}

footer {
  @apply h-16 bg-gray-800
}
</style>