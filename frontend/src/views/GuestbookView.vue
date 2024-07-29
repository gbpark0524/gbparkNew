<template>
  <div class="container">
    <div class="paper-back">
    </div>
    <div class="paper-front">
      <h3>Guestbook</h3>
      <div class="contents">
        <form @submit.prevent="submitForm" ref="form">
          <div class="div-form">
            <label>Title</label>
            <input type="text" v-model="formData.title"/>
          </div>
          <div class="div-form">
            <label>Name</label>
            <input type="text" v-model="formData.writer"/>
          </div>
          <div class="div-form">
            <label>Email</label>
            <input type="email" v-model="formData.email"/>
          </div>
          <div class="div-form">
            <label>Dear gbPark.</label> <br/>
            <textarea rows="14" v-model="formData.content"></textarea>
          </div>
          <div class="div-form"><button type="submit" class="pen-fountain"></button></div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import axios from 'axios';
const formData = ref({ title: '', writer:'', email: '', content:'' });

const submitForm = async () => {
  try {
    const response = await axios.post('/board/guestbook', formData.value);
    alert(response);
    formData.value = { title: '', writer:'', email: '', content:'' };
  } catch (error) {
    if (axios.isAxiosError(error)) {
      const errorMessage = error.response?.data?.message ?? 'An unexpected error occurred';
      alert(errorMessage);
    } else {
      alert('An unexpected error occurred');
    }
  }
};

onMounted(() => {
});
</script>

<style scoped lang="scss">
body {
  background: #efefef;
  line-height: 32px;
  color: #666;
}

.container {
  @include flex-center;
  padding : 125px 0;
}

.paper-back, .paper-front {
  width: 1000px;
  height: 700px;
  box-shadow: 0 2px 2px #ccc;
  background: #fff;

  @include mo--screen {
    width: 80%;
    transform: rotate(-10deg);
  }

  &.paper-back {
    transform: rotate(-15deg);
    position: relative;
  }

  &.paper-front {
    position: absolute;
    transform: rotate(3deg);
    background-size: 144% 133%;
    display: flex;
    flex-direction: column;

    h3 {
      display: block;
      width: 55%;
      margin: 50px auto 0;
      text-align: center;
      color: #666;
      word-spacing: 5px;
      text-shadow: 0 2px #efefef;
      border-bottom: 4px double;
    }
  }
}

.contents {
  width: 80%;
  margin: 10px auto;
  flex: 1 auto;

  .div-form {
    margin-top: 10px;
  }
}

input, textarea {
  display: block;
  width: 100%;
  outline: none;
  padding: 5px 0;
  border: none;
  border-bottom: 2px dotted #ccc;
}

textarea {
  border-bottom: none;
}

.pen-fountain {
  width: 48px; /* 원하는 크기로 설정 */
  height: 48px; /* 원하는 크기로 설정 */
  background: url('@/assets/images/icons/Fountain pen.svg') no-repeat center center;
  background-size: contain;
  border: none;
  cursor: pointer;
  float: right;

  &:hover {
    opacity: 0.8;
  }
}

</style>