<template>
  <div id="app">
    <AppHeader />
    <router-view />
    <BottomNav />
  </div>
</template>

<script>
import AppHeader from './components/AppHeader.vue';
import BottomNav from './components/BottomNav.vue';

export default {
  components: {
    AppHeader,
    BottomNav
  }
};
</script>

<style lang="scss">
@import "@/assets/styles/_variables.scss";

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
}
</style>
