<!--Home-->
<template>
<div class="home">
  <img alt="Vue logo" src="../assets/logo.png">
  <h1><HelloWorld msg="Hello World!"/></h1>
</div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'PageHome',
  components: {
    HelloWorld
  }
}
</script>