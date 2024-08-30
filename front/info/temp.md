# 강의

1.오라클 도커 실행

docker exec -it OraFree sqlplus

2.로그인

sys as sysdba
as

3.계정 확인

show usser


4.계정 sys 제대로 나오면, @+경로 입력
-도커
@opt/oracle/product/11.2.0/xe/rdbms/admin/utlsampl.sql
-윈도우
@C:\oraclexe\app\oracle\product\11.2.0\server\rdbms\admin\scott.sql

5.계정 접속
CONN scott/tiger;

6.'' 테이블 구성 확인
DESC <테이블>; 테이블= EMP


탐색기에서 `+` 눌러서 데이터소스 soctt으로 생성.
프로젝트명은 노상관
사용자만 맞추면 됨.
호스트 = localhost
포트 = 1521



MVC 핸들러,리졸버 등 뭐하는 애들인지

db 연동하는거
dto,vo
롬복 게터세터 생성자
인터페이스로 dao 만들어서 crud
dao impl로 crud 오버라이드
DML 명령 처리 클래스 'jdbc템플릿' : crud 4대구문

디스패처서블릿은 웹xml에 등록해야됨.

어노테이션기반 : bean으로 dao 하자
그러려면
설정:
1.  context:annotation-config
2. 어노테이션 스캔
3. 그리고 db,영속성 db 다 쓸려면,
   스프링 설정파일 두개이상이면,

리스너 꼭 등록해야됨.

디스패처서블릿이 가진
context param으로

빈 하위에 프로퍼티에
드라이버,url,name.pw
얘네는 빈의 세터메소드,?



컨트롤러 어노테이션 붙혀도
설정파일가서 컨트롤러를 등록해야댐?

리퀘스트 매핑으로 write 메서드 쓰려면
xml 에서 등록해야댐?


음 h2라 그런가? 테이블을 계속 드랍 하고 생성해서?
save 누를때마다 프라이머리키인 idx가 올라간다.

https://f-lab.kr/insight/frontend-backend-separation

프론트와 백엔드 분리의 구조와 이해. 장점과 미래


json과 js : obj의 차이
{
키:값
}
인데
json은 키에 ""로 감싸져 있다.


----
json은 기본이 스네이크 케이스다
phone_number

json에 value에 올 수 있는 타입은
String, number, boolean, object{}, array{}

number?-> long int flot 다 포함된건가



배열타입 생긴거 보고 떠오른 생각:
혹시 json파일에서 제어문이 있을까?




json은 키가 "" 로 감싸져있으니까 무적권 String 타입임

람다 forEach문에 변수가 하나면은 걍 ()->{} 라고 해도됨




@PutMapping
responseEntity
리스폰스엔티티 컨트롤클릭으로 잘 들여다봐야한다.

splirUp -> 자동 증가
jdbcTeamplate.batchUpdate.안에 있는 메서드










# ETC


RequiredArgsConstructor란?

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
얜 모지?

HandlerMethodArgumentResolver
얜 모지?

addArgumentResolvers
얜 모지?
@PostConstruct란?

@Transactional란?

main.java.srcDirs += [ generated ]
srcDirs....? 이게 모지?

RepositoryCustom은 @Autowired 안해줘도 되나? (QueryDsl 설정할때.) -> 해야되더라

search_key: this.$route.query.sk ? this.$route.query.sk : '',
search_value: this.$route.query.sv ? this.$route.query.sv : '',

getter같은건가...? 생성자 함순가...?

---
쿼리스트링에는 {id}가 없는데,
어떻게 식별하고 캡쳐해서 값을 가져오게 되는거지?

id라는 벨류에 담아서 가져오니까
id값만 갖고 바디에 리스폰 하는걸텐데 말야
근데 그걸 어디서 어디서 어떻게 캡처해왔냐는거지



---
AOP에 대한거 알아보자

DIE에 대한거 알아보자

responseApiController

프로퍼티 말고 yml로 설정해서 광명찾자

@JsonNameing(value.property=~~~)
스네이크케이스로 제이슨 키값 만들고 싶을때 씀

request 요청 받으면 -> 오브젝트 멤버가 작동이 돼서 오브젝트로 받아서 메서드로 변환시키고 오브젝트 매퍼가 제이슨으로 바꾸고 리스폰함

---

---
## 검색 기능



Data JPA가 제공해주는 Paging처리 해주는 인터페이스가 있다.
PageAble , Page
$주의 (awt도 이 기능이 있으니 import 확인 잘 해주자.)

내 경우는
getPageNumber()가 없고,
getNumberOfPage() 가 있길래
버전 업뎃되면서 메서드 이름이 바뀌었나 싶었다. 그래서 다 얼추 다 작성하고 돌려보니 컴파일 에러가 났다.
내가 업뎃 된 PageAble을 잘못 사용했나 싶었는데, 그게 아니라
ㅅㅂ 자바 awt의 PagAble 인터페이스를 임포트하고 있었다는 것을 확인했다.

service,WebConfig,Pagination 클래스들 import 확인하고 마지막으로 내가 만든 JPA Repository 인터페이스에서 awt의 PageAble을 임포트 하고 있던 것을 확인.

그 부분을 고치니까 잘 됐다.

PageAble 관련 학습은 여기 좋아뵘
https://wonit.tistory.com/483
https://hudi.blog/spring-data-jpa-pagination/

back  끝.



front도 수정해주자.
BoardList.vue 파일에서 fnGetList 수정해주면 된다.

.then((res) => {
this.list = res.data.data   에서

.then((res) => {
if (res.data.["result_code"] === "OK"){
this.list = res.data.data
this.paging = res.data.["pagination"]
this.no = this.paging.total_list_cnt - ((this.paging.page - 1) * this.paging.page_size)
})
//서버에서 데이터를 목록(data)으로 보내므로 바로 할당하여 사용할 수 있다. -> if문 추가해서 header로 OK 들어오면 목록(data)에서 가공된 목록(data.data)을 뿌림.
*[" "] 이렇게 감싼건. vue파일에서 권장하는 듯하여 감쌋다. 안감싸도 정상실행은 된다.


$결과. warning
일단 디비 갯수가 10개 밖에 없어서 그런지 1페이지만 보인다. 그리고 여전히 등록은 안되는 상황..
write.vue 에서 this.$axios.  ***  (apiUrl, this.form)
이부분이 스프링에서 put으로 했기 때문에 put으로 해봤는데 작동이 안되고, add로 바꾸니 런타임에러가 난다.
일단 put이 맞는 것 같고, 다음 로직에서 찐빠난게 있을 것 같다.
네트워크 콘솔 보면은 계속 write:1로 보내는데,
id값을 계속 1로 보내서 그런 것 같다.
idx 중복 안되게 해놨기 때문에.
요청 id값 중복시 제일 높은 id값 찾은 후 그 지점에서 auto-increment 하게 해주자.


일단 디비레코드 1000개 정도만들어보자.
JPA가 알아서 해주게끔 해준다.
보드 @Repository,@Entity에 이어서
BoardInitializer 클래스를 만들어서 @Service로 등록해준다.
그리고 거기에 @Repository 가져와서 생성자 만들어주고,[생성자 안만들고 @autoWired로 해도됨.]

@PostConstruct
@Transactional
public void insertInitialData(){
if (boardRepository.count() == 0) {
for (int i = 1; i <= 100; i++) {
BoardEntity board = new BoardEntity();
board.setTitle("Title " + i);
board.setContents("Contents " + i);
board.setAuthor("Author " + i);
board.setCreatedAt(LocalDateTime.now());
boardRepository.save(board);
}
}

다 잘 되는데 << <1~100 > >>
여기 페이지 앞뒤 건너띄기랑 맨앞뒤 건너뛰기에서 앞,맨앞 건너뛰기하면 에러남. 크롬 콘솔보니까 이건 프론트에서 잡아줘야 될 거 같음. 아무래도 오타에러 일거같음
'javascript:;' 라고 뜸.

!! 해결_세미콜론 지우니까 됨.

그리고 detail{id} 들어갔다가 뒤로 돌아가기 하면 pagination이 아묻따 1페이지를 잡는다.
(만들어둔 목록으로 돌아가기 버튼으로 빠져나오면 잘 동작함.)

!! 해결_ 이것도 "javascript:;"에서 세미콜론 지우니까 됨.


detail{id}가 해당하는 페이지로 돌아가게 하자.
생각나는 방법은 세션에 히스토리 저장해서 구현하기
js에서 알아서 해주는 메소드가 있을 것 같긴함.
-성공!



----------------------------------------------------
# 검색 구현.


## 게시글을 가져오는 순서.
검색 data 탐색 ->
1. if(키값 대조해서 맞으면) {해당 정보 리스트에 담아 가져옴}
2. if(null) {기본 리스트 반환}

## 방법
1. 직접 쿼리 짜기.
2. JPA + QueryDSL로 자바 클래스랑 메서드로 조작하기
   -2번 채택.
   QueryDS: 스프링 코드레벨에서 쿼리 작성하게 해줌.
   가독성과 유지보수성을 높힘 작성도 쉬움. JPA 쓸 때 이거 안쓰면 손해임.
   쿼리 관련 라이브러리들 4가지(JPA,NavieQuery,JPQL,QueryDSL)이렇게 있는데
   4개 코드 짠거 한눈에 보면 비교하기 좋음.



덤으로 코드로 쿼리 작성하는거라 컴파일때 에러 찾아낼 수 있는게 goat임.

## Todo

1. build.gradle에 QueryDSL과 관련된 부분을 추가.
2. config 패키지에 하위에 QuerydslConfig 클래스 추가. (Repository에서 관리하게끔.)
3. model 패키지 하위에 SearchConfition 클래스 추가.
   4.entity 패키지 하위에 BoardRepositoryCustom 클래스 추가. 기존에 쓰던 @Repository는 별개로 냅두고,
   JPAQueryFactory를 구현시켜서 쓰자.
5. BoardService에  BoardRepositoryCustom 가꼬와서
   getBoardList에 searchConfition 추가해준다.
6. Front에서 BoardList.vue에서 태그,검색 조건, 검색 버튼 추가.



----------------------------------------------------
1.

tasks.withType(JavaCompile).configureEach {
options.annotationProcessorGeneratedSourcesDirectory = file(generated)
}


설정해 줘야 할 것.
- 그레들 최상단(plugins 위)에 QueryDsl버전 빌드스크립트 추가해줘야됨.buildscript{ (ext {queryDslVersion = "5.0.0"}}
- Qclass 저장할 패키지 경로,
- Qclass 생성할 패키지 경로.
- JAVAsourceSets에 Qclass 위치 알려주기.
  +- 그레이들 클린시 Qclass도 같이 없애기 기능.

이거 다 해주고 빌드 한번 해보면 gerrated 소스 폴더 하나 생기는데, 거기에 Entity에 등록해뒀던 필드,서비스들  QClass로 쫘라락 생김.

그리고 config 패키지 하위에 QueryDslConfig 클래스 생성하고 코끼리 또 빌드.



`빌드 했더니 jdk도 없고
javax.persistence.Entity 없다고 빌더가 찡찡댄다면 스프링 2.0의 세팅일거임.
스프링 3.x랑 세팅이 다름.
`

ref: https://velog.io/@clickyour/SpringBoot-QueryDSL-%EC%82%AC%EC%9A%A9%EB%B2%95


https://velog.io/@juhyeon1114/Spring-QueryDsl-gradle-%EC%84%A4%EC%A0%95-Spring-boot-3.0-%EC%9D%B4%EC%83%81


https://honeymon.io/tech/2020/07/09/gradle-annotation-processor-with-querydsl.html

이 블로그들 참고해서 작성하면 해결됨



2.
이제 클라보내는 검색조건(검색정보) 받아줄 모델이 필요함.
model 패키지 하위에 SearchCondition class 생성.


3.
QueryDSL의 Repository로 쓸 녀석이 필요함.
기존엔 interface로 만들었지만
얜 class 파일로 생성해서 JPAQueryFactory 가져와서 구현해야함.


entity 패키지 하위에 BoardRepositoryCustom 생성
검색기능과 예외처리 2개 method 만들어준다.

JPAQueryFactory
PageImpl
BooleanExpression
StringUtils.hasLength()


4. crud 뽑을 때 boardService에서 boardRepository를 통해 데이터를 가져왔던 것 처럼.
   search는 boardRepositoryCustom를 통해서 가져오면 된다.

boardService에서 Header<> getBoardList()에 searchCondition을 추가해주자.


그리고 BoardController 가서
searchCondition를 적절히 추가해준다.

@Getmapping
Header<List<BoardDto>> boardList(
){}
여기에.

근데 RepositoryCustom은 @Autowired 안해줘도 되나봄?


back 끝
-----------------------------------
front

# 1. fnGetList()에 데이터 전송할 애들 박는다.
## back에서 SearchCondition 에 만들어뒀던 필드멤버 두개 매칭시켜주자.

우선 search_key,value 얘네 페이징 데이터에 생성자 만들어주자.
search_key: this.$route.query.sk ? this.$route.query.sk : '',
search_value: this.$route.query.sv ? this.$route.query.sv : '',
이렇게 만들면 되는데, getter같은건가...? 생성잔가...?

근데  fnGetList()에 프로퍼티만 잘 박아주면 얘네 없어도 검색은 잘됨

이제 fnGetList() 메서드에 프로퍼티 생성.

sk: this.search_key,
sv: this.search_value,



이제 api 완성임.


###  검색 ui form 만들자.

위치는 pagination 밑에 만들어주면 됨.


select 태그에 v-model로 search_key 박자

옆에 text input 으로 search_value 넣을 공간 만들어서 마찬가지로 v-model에 박고
@keyup.enter="fnPage()

이거 해주면
그 커서 입력된 곳에서 엔터 눌러주면 fnPage() 메서드로 전송됨.

그리고 눈에 보이는 버튼 태그도 만들어주고 거기에 온클릭 으로 fnPage() 메서드 넣어주자.

뒤로가기해서 바로 전 페이지로 이동되는게 아니라 list가 새로 불러와지는 상황 다시 발생.

어제 일지 보고 다시 고쳐놓자.


to do feat primaryValue in select tag.

todo add gitConvention setting [gitmessage.txt]

ref:
https://step-by-step-noah.tistory.com/46

https://hyunjun.kr/21

-----------------------------------

검색기능 끝




---

---

---

---

---

---

---

---

---

---

---

---




