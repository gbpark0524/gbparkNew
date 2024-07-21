<template>
  <header :class="{ sticky: isSticky }">
    <nav>
      <div class="menu-icon" @click="toggleMenu">
        <div class="bar"></div>
        <div class="bar"></div>
        <div class="bar"></div>
      </div>
      <ul :class="{ open: isMenuOpen }">
        <li><router-link to="/" @click="closeMenu">Home</router-link></li>
        <li><router-link to="/guestbookMain" @click="closeMenu">guestbook</router-link></li>
        <li><a href="#projects" @click="closeMenu">Projects</a></li>
        <li><a href="#contact" @click="closeMenu">Contact</a></li>
      </ul>
    </nav>
  </header>
</template>

<script lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';

export default {
  name: 'AppHeader',
  setup() {
    const isMenuOpen = ref<boolean>(false);
    const isSticky = ref<boolean>(false);

    const toggleMenu = () => {
      isMenuOpen.value = !isMenuOpen.value;
    };

    const closeMenu = () => {
      isMenuOpen.value = false;
    };

    const handleScroll = () => {
      isSticky.value = window.scrollY > 0;
    };

    onMounted(() => {
      window.addEventListener('scroll', handleScroll);
    });

    onBeforeUnmount(() => {
      window.removeEventListener('scroll', handleScroll);
    });

    return { isMenuOpen, isSticky, toggleMenu, closeMenu };
  }
};
</script>

<style scoped lang="scss">
header {
  position: relative;
  width: 100%;
  background: #333;
  color: white;
  &.sticky {
    position: fixed;
    top: 0;
    z-index: 1000;
  }
  nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 1rem;
  }
  .menu-icon {
    display: none;
    flex-direction: column;
    cursor: pointer;
    .bar {
      width: 25px;
      height: 3px;
      background-color: white;
      margin: 4px 0;
    }
  }
  ul {
    list-style: none;
    display: flex;
    li {
      margin: 0 1rem;
    }
    a {
      color: white;
      text-decoration: none;
    }
  }
  @media (max-width: 768px) {
    .menu-icon {
      display: flex;
    }
    ul {
      display: none;
      flex-direction: column;
      position: absolute;
      top: 100%;
      right: 0;
      background: $primary-color;
      width: 100%;
      &.open {
        display: flex;
      }
      li {
        margin: 1rem 0;
      }
    }
  }
}
</style>
