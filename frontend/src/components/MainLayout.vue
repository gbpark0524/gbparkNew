<script lang="ts">
import Header from './AppHeader.vue'
import Footer from './BottomNav.vue'

export default {
  components: {
    Header,
    Footer,
  }
}
</script>

<template>
  <div>
    <Header />
    <div class="main-content">
      <router-view />
    </div>
    <Footer />
  </div>
</template>

<style scoped lang="scss">

</style>