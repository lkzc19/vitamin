<script setup lang="ts">
const route = useRoute()
const value = ref('')
</script>

<template>
  <UContainer class="min-h-screen flex items-center">
    <UCard
        class="flex-1"
        :ui="{
        background: 'bg-gray-50 dark:bg-gray-800/50',
        ring: 'ring-1 ring-gray-300 dark:ring-gray-700',
        divide: 'divide-y divide-gray-300 dark:divide-gray-700',
        header: { base: 'font-bold' },
      }"
    >
      <template #header>
        <div>
          <UInput color="gray" variant="none" placeholder="Search..." />
          <UButton color="primary" variant="solid">Button</UButton>
        </div>
      </template>

      <div>
        <div>

        </div>
        <div>
          <ULink
              to="http://www.baidu.com"
              active-class="text-primary"
              inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
          >
            Link
          </ULink>
          <ULink
              to="http://www.baidu.com"
              active-class="text-primary"
              inactive-class="text-gray-500 dark:text-gray-400 hover:text-gray-700 dark:hover:text-gray-200"
          >
            Link
          </ULink>
        </div>
      </div>
    </UCard>
  </UContainer>
</template>

<style>
body {
  @apply antialiased font-sans text-gray-700 dark:text-gray-200 bg-white dark:bg-gray-900;
}
</style>