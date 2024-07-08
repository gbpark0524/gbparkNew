<template>
  <div id="content">
    <div class="content-item" v-for="item in items" :key="item.id">{{ item.text }}</div>
    <div v-if="loading" class="loading">Loading...</div>
  </div>
</template>

<script>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

export default {
  name: 'AppContent',
  setup() {
    const items = ref([]);
    const page = ref(1);
    const loading = ref(false);

    const loadMore = () => {
      if (loading.value) return;
      loading.value = true;
      axios.get(`https://reqres.in/api/users?page=${page.value}`).then(response => {
        items.value.push(...response.data.data);
        page.value++;
        loading.value = false;
      });
    };

    const handleScroll = () => {
      if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        loadMore();
      }
    };

    onMounted(() => {
      window.addEventListener('scroll', handleScroll);
      loadMore();
    });

    onBeforeUnmount(() => {
      window.removeEventListener('scroll', handleScroll);
    });

    return { items, loading };
  }
};
</script>

<style scoped lang="scss">
#content {
  .content-item {
    padding: 1rem;
    border-bottom: 1px solid #ccc;
  }
  .loading {
    text-align: center;
    padding: 1rem;
  }
}
</style>
