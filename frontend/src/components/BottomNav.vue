<template>
  <div id="bottom-nav" class="bottom-nav">
    <a href="#home">Home</a>
    <a href="#about">About</a>
    <a href="#projects">Projects</a>
    <a href="#contact">Contact</a>
  </div>
</template>

<style scoped lang="scss">
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
  background: $primary-color;
  color: white;
  display: flex;
  justify-content: space-around;
  padding: 0.5rem 0;
  a {
    color: white;
    text-decoration: none;
  }
  @media (min-width: 769px) {
    display: none;
  }
}
</style>
