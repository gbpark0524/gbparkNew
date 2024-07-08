<template>
  <div id="app">
    <AppHeader />
    <router-view />
    <BottomNav />
  </div>
</template>

<script lang="ts">
import { Options, Vue } from 'vue-class-component';
import AppHeader from './components/AppHeader.vue';
import BottomNav from './components/BottomNav.vue';

@Options({
  components: {
    AppHeader,
    BottomNav
  },
})
export default class App extends Vue {}
</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  color: #2c3e50;
}
</style>
