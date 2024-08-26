<template>
  <div>
    <div>
      <h2>Please Log In</h2>
      <div id="loginForm">
        <form @submit.prevent="fnLogin">
          <p>
            <input class="w3-input" name="uid" placeholder="Enter your ID" v-model="user_id"><br>
          </p>
          <p>
            <input name="password" class="w3-input" placeholder="Enter your password" v-model="user_pw" type="password">
          </p>
          <p>
            <button type="submit" class="w3-button w3-green w3-round">Login</button>
          </p>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex' //vuex 추가

export default {
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  methods: {
    ...mapActions(['login']),     //vuex/actions에 있는 login 함수

    async fnLogin() { //fnLogin() 에서 async 함수로 변경.
      if (this.user_id === '') {
        alert('ID를 입력하세요.')
        return
      }

      if (this.user_pw === '') {
        alert('비밀번호를 입력하세요.')
        return
      }
// request Login API
      try {
        let loginResult = await this.login({user_id: this.user_id, user_pw: this.user_pw});
        if (loginResult)  alert('로그인 결과 : ' + loginResult)
          alert('로ㅓ그인 결과 : ' + loginResult);
      } catch (err) {
        if (err.message.indexOf('Network Error') > -1) {
          alert('서버 상태 확인')
        } else {
          alert('로그인 정보 확인불가ㅣ')
        }
      }
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>

<style>
#loginForm {
  width: 500px;
  margin: auto;
}
</style>