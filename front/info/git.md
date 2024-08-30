
# Commit Convention
   <타입> 리스트
-   feat        : 기능 (새로운 기능)
-   fix         : 버그 (버그 수정)
-   refactor    : 리팩토링
-   design      : CSS 등 사용자 UI 디자인 변경
-   comment     : 필요한 주석 추가 및 변경
-   style       : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
-   docs        : 문서 수정 (문서 추가, 수정, 삭제, README)
-   test        : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
-   chore       : 기타 변경사항 (빌드 스크립트 수정, assets, 패키지 매니저 등)
-   init        : 초기 생성
-   rename      : 파일 혹은 폴더명을 수정하거나 옮기는 작업만 한 경우
-   remove      : 파일을 삭제하는 작업만 수행한 경우





# 🌴 Commit Template 적용

1. 본인이 원하는 경로에 gitmessage.txt 파일을 생성한다.
2. 내용은 위 템플릿 내용을 넣는다.(#은 주석임)
3. 터미널에 명령을 입력한다.
   $ git config --global commit.template C:\.gitmessage.txt (예시를 위해 C:\ 경로를 주었고 실제로는 본인 경로에다가!)

---

ref:

- https://overcome-the-limits.tistory.com/entry/%ED%98%91%EC%97%85-%ED%98%91%EC%97%85%EC%9D%84-%EC%9C%84%ED%95%9C-%EA%B8%B0%EB%B3%B8%EC%A0%81%EC%9D%B8-git-%EC%BB%A4%EB%B0%8B%EC%BB%A8%EB%B2%A4%EC%85%98-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0