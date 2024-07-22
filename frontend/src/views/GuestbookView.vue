<script setup lang="ts">

</script>

<template>
  <div class="container">
    <div class="paper-back">
    </div>
    <div class="paper-front">
      <h3>Guestbook</h3>
      <div class="contents">
        <form>
          <div class="div-form">
            <label>Name</label>
            <input type="text" />
          </div>
          <div class="div-form">
            <label>Email</label>
            <input type="email" />
          </div>
          <div class="div-form">
            <label>Dear gbPark</label> <br/>
            <textarea rows="17" name="comments"></textarea>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

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

input[type="text"], input[type="email"], textarea {
  display: block;
  width: 100%;
  outline: none;
  padding: 5px 0;
  border: none;
  border-bottom: 2px dotted #ccc;
}
</style>