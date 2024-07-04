<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <div class="test"></div>
  {{test2}}
  <HelloWorld msg="Welcome to Your Vue.js App"/>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import axios from 'axios';

export default {
  name: 'App',
  data() {
    return {
      test2 : ''
    };
  },
  methods: {
    test() {
      axios.get('/api/test')
          .then(response => {
            this.test2 = response.data;
          })
          .catch(error => {
            console.error("There was an error fetching the projects!", error);
          });
    }
  },
  mounted() {
    this.test();
  },
  components: {
    HelloWorld
  }
}
</script>

<style>
#app {
  font-family: "Noto Sans", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
<style lang="scss">
@import "@/styles/_variables.scss";

.test {
  width: 100px;
  height: 100px;
  background-color: $primary-color;
}
</style>
