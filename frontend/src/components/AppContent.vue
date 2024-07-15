<template>
  <div id="content">
    <h4 id="title">타이틀</h4>
    <a href="https://www.naver.com">링크</a>
    <button id="btn">버튼</button>
    <div class="content-item" v-for="item in items" :key="item.id">{{ item.first_name }}</div>
    <div v-if="loading" class="loading">Loading...</div>
  </div>
</template>

<script lang="ts">
import { ref, onMounted, onBeforeUnmount } from 'vue';
import axios from 'axios';

interface Item {
  id: number;
  first_name: string;
  last_name: string;
  email: string;
  avatar: string;
}

interface Page {
  data: Item[];
}
export default {
  name: 'AppContent',
  setup() {
    const items = ref<Item[]>([]);
    const page = ref(1);
    const loading = ref(false);
    
    const loadMore = () => {
      if (loading.value) return;
      loading.value = true;
      axios.get<Page>(`https://reqres.in/api/users?page=${page.value}`).then(response => {
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
