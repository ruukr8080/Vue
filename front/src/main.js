import './assets/common.css'
// import './assets/w3.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios' // :8000/{id}'에 id값 들오면 @RestController가 getBoard()메서드에 빌더로 뽑은 애들 다 제이슨 형식으로 만들고
//엑시오스가 그걸 :4500/쿼리스트링으로 바꾸고 라우터로 뿌리는거였음.
import store from './vuex/store'  //1. store 추가

const app = createApp(App)
app.config.globalProperties.$axios = axios; //엑쇼스를 글로벌로 설정. 이럼 컴포넌트에서 this.$axios 로 호출 가능
app.config.globalProperties.$serverUrl = '//localhost:8000' //스프링이 쓰는 포트. api server
app.config.globalProperties.$store = store // 스토어
app.use(router)
    .use(store) //2.store 등록
    .mount(`#app`)
