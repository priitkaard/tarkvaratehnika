<template>
  <div id="app">
    <Hero />
    <HomePage />
  </div>
</template>

<script>
import Hero from './components/Hero';
import HomePage from './views/HomePage.vue'

export default {
  name: 'app',
  components: {
    Hero,
    HomePage
  }
}
</script>

<style>
  html, body {
    margin: 0;
  }
</style>
