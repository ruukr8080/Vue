<template>
  <div class="board-list">
    <div class="common-buttons">
      <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnWrite">등록</button>
    </div>
    <table class="w3-table-all">
      <thead>
      <tr>
        <th>No</th>
        <th>제목</th>
        <th>작성자</th>
        <th>등록일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(row, idx) in list" :key="idx">
        <td>{{ row.idx }}</td>
        <td><a v-on:click="fnView(`${row.idx}`)">{{ row.title }}</a></td>
        <td>{{ row.author }}</td>
        <td>{{ row.created_at }}</td>
      </tr>
      </tbody>
    </table>

    <div class="pagination w3-bar w3-padding-16 w3-small" v-if="paging.total_list_cnt > 0">
      <span class="pg">
      <a href="javascript:" @click="fnPage(1)" class="first w3-button w3-bar-item w3-border">&lt;&lt;</a>
      <a href="javascript:" v-if="paging.start_page > 10" @click="fnPage(`${paging.start_page-1}`)"
         class="prev w3-button w3-bar-item w3-border">&lt;</a>

      <template v-for=" (n,index) in paginavigation()">
          <template v-if="paging.page==n">
              <strong class="w3-button w3-bar-item w3-border w3-green" :key="index">{{ n }}</strong>
          </template>
          <template v-else>
              <a class="w3-button w3-bar-item w3-border" href="javascript:" @click="fnPage(`${n}`)"
                 :key="index">{{ n }}</a>
          </template>
      </template>
      <a href="javascript:" v-if="paging.total_page_cnt > paging.end_page"
         @click="fnPage(`${paging.end_page+1}`)" class="next w3-button w3-bar-item w3-border">&gt;</a>
      <a href="javascript:" @click="fnPage(`${paging.total_page_cnt}`)" class="last w3-button w3-bar-item w3-border">&gt;&gt;</a>
      </span>
    </div>
    <!--    검색 form  -->
    <div>
      <select v-model="search_key" value="선택">
        <option value="">- 선택 -</option>
        <option value="author">작성자</option>
        <option value="title">제목</option>
        <option value="contents">내용</option>
      </select>
      &nbsp;
      <input type="text" v-model="search_value" @keyup.enter="fnPage()">
      &nbsp;
      <button @click="fnPage()">검색</button>
    </div>

  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      no: '', //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 1,
      size: this.$route.query.size ? this.$route.query.size : 10,
      search_key: this.$route.query.sk ? this.$route.query.sk : '',
      search_value: this.$route.query.sv ? this.$route.query.sv : '', // 얘네 없어도 검색 잘되네 왜지
      // keyword: this.$route.query.keyword, //뭐하는 애였지 진짜
      paginavigation: function () { //페이징 처리 for문 커스텀
        let pageNumber = [] //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    this.fnGetList()
  },
  methods: {
    fnGetList() {
      this.requestBody = { // 데이터 전송
        sk: this.search_key,
        sv: this.search_value,
        // keyword: this.keyword, //얘 용도가 뭐였지? [08.23]
        page: this.page,
        size: this.size
      }
      this.$axios.get(this.$serverUrl + "/board/list", {
        params: this.requestBody,
        headers: {}
      }).then((res) => {
        if (res.data["result_code"] === "OK") {
          this.list = res.data.data  //pageList. 목록임. - 서버에서 데이터를 목록(data)으로 보내므로 바로 할당하여 사용할 수 있다. -> if문 추가해서 header로 OK 들어오면 목록(data)에서 가공된 목록(data.data)을 뿌림.
          this.paging = res.data["pagination"] //pagination. 밑에 페이지 번호와 카운트. - 로직 다시 살펴보셈
          this.no = this.paging.total_list_cnt - ((this.paging.page - 1) * this.paging.page_size) //페이져블 다시 살펴보자.
        }
      }).catch((err) => {
        if (err.message.indexOf('Network Error') > -1) {
          alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
        }
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        this.page = n
      }
      this.fnGetList()
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: this.requestBody
      })
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      });
    }
  }
}
</script>


<!--
<script setup>
import {ref, computed} from 'vue';
// import {useRoute} from 'vue-router';
// // import $axios from "core-js/internals/queue";
// import axios from "$axios";
// import * as error from "express";
// // import axios from "axios";
//
// const route = useRoute();
//
// const requestBody = ref({});
// const list = ref({});
// // eslint-disable-next-line no-unused-vars
// const no = ref('');
// const paging = ref({
//   block: 0,
//   end_page: 0,
//   next_block: 0,
//   page: 0,
//   page_size: 0,
//   prev_block: 0,
//   start_index: 0,
//   start_page: 0,
//   total_block_cnt: 0,
//   total_list_cnt: 0,
//   total_page_cnt: 0,
// });
//
// const page = ref(route.query.page ?? 1);
// const size = ref(route.query.size ?? 5);
// const keyword = ref(route.query.keyword);
//
// const paginavigation = computed(() => {
//   const pageNumber = [];
//   const start_page = page.value.start_page;
//   const end_page = page.value.end_page;
//   for (let i = start_page; i <= end_page; i++) {
//     pageNumber.push(i);
//   }
//   return pageNumber;
// });
//
// const fnGetList = () => {
//   requestBody.value = {
//     page: page.value,
//     size: size.value,
//     keyword: keyword.value
//   };
// axios.get('/board/list',{
//   params: requestBody.value,
//   headers:{}
// }).then((res) => {
//   list.value = res.data;
// }).catch((err) => {
//     console.error("fucking ERROR",err);
//     alert("fucking ERROR");
//     if (err.response){
//       console.error('status code :', error.response.status);
//       console.error('data :',error.response.data);
//       console.error('header :',error.response.headers);
//       if (err.response.status === 404) {
//         alert('요청한 게시글 목록을 찾을 수 없습니다.');
//       } else if (err.response.status === 500) {
//         alert('서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.');
//       } else {
//         alert('게시글 목록을 가져오는 중 오류가 발생했습니다.');
//       }
//     } else if (err.request) {
//       // 요청이 이루어 졌으나 응답을 받지 못했습니다.
//       console.error('응답을 받지 못함:', err.request);
//       alert('서버로부터 응답이 없습니다. 인터넷 연결을 확인하거나 잠시 후 다시 시도해주세요.');
//     } else {
//       // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
//       console.error('요청 설정 중 에러 발생:', err.message);
//       alert('요청 설정 중 문제가 발생했습니다.');
//     }
//   });
// };
// fnGetList();
</script>
-->

