<template>
<div class="board-detail">
  <div class="common-buttons">
    <button v-on:click="fnSave" type="button" class="w3-button w3-round w3-blue-gray">save</button>
    <button v-on:click="fnList" type="button" class="w3-button w3-round w3-gray">list</button>
  </div>
  <div class="board-contents">
    <input v-model="title" class="w3-input w3-border" placeholder="title 쓰셈"/>
    <input v-if="!idx" v-model="author" class="w3-border w3-input" placeholder="author 쓰셈">
  </div>
  <div class="board-contents">
    <textarea v-model="contents" class="w3-border w3-animate-input" cols="30" rows="10" style="resize: none"></textarea>
  </div>
  <div class="common-buttons">
    <button v-on:click="fnSave" type="button" class="w3-button w3-round w3-blue-gray">save</button>
    <button v-on:click="fnList" type="button" class="w3-button w3-round w3-gray">list</button>
  </div>
</div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,

      title: '',
      author: '',
      contents: '',
      created_at: ''
    }
  },
  mounted() {
    this.fnGetView()
  },
  methods: {
    fnGetView() {
      if (this.idx !== undefined) {
        this.$axios.get(this.$serverUrl + '/board/' + this.idx, {
          params: this.requestBody
        }).then((res) => {
          this.title = res.data.title
          this.author = res.data.author
          this.contents = res.data.contents
          this.created_at = res.data.created_at
        }).catch((err) => {
          console.log(err)
        })
      }
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnSave() {
      let apiUrl = this.$serverUrl + '/board'
      this.form = {
        "idx": this.idx,
        "title": this.title,
        "contents": this.contents,
        "author": this.author
      }

      if (this.idx === undefined) {
        //INSERT put이랑 post 중에 고르자.
        this.$axios.put(apiUrl, this.form)
            .then((res) => {
              alert('글이 저장되었습니다.')
              this.fnView(res.data.idx)
            }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.\n id값 중복돼서 안되는거임.\n id값 제일 높은 숫자 찾아서 그 지점에서 자동증가 먹여야됨.')
          }
        })
      } else {
        //UPDATE
        this.$axios.patch(apiUrl, this.form)
            .then((res) => {
              alert('글이 저장되었습니다.')
              this.fnView(res.data.idx)
            }).catch((err) => {
          if (err.message.indexOf('Network Error') > -1) {
            alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>