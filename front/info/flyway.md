# Flyway
Flyway는 데이터베이스의 형상 관리를 목적으로 하는 오픈소스 마이그레이션 툴

# Flyway를 도입한 이유

기존 프로젝트의 환경별 데이터베이스 상황은 아래와 같다.


local - H2 Database, ddl-auto: create
dev - RDS(MySQL), ddl-auto: update
prod - RDS(MySQL), ddl-auto: none (최초에는 ddl-auto:create을 통해 생성)


환경마다 ddl-auto 옵션이 다르기 때문에 엔티티 구조가 변경되었을 때 
반영이 되지 않아 conflict가 발생하는 경우가 종종 있었다.

단순히 개발 단계에서는 테이블을 날리고 다시 실행하는 것도 방법이긴 하지만 
이렇게 하면 선배님들한테 뺨맞는다.
그렇기 때문에 Flyway를 통해 테이블 변경에 유연하게 데이터베이스를 관리해보고자 한다.





# Flyway 동작 원리

Flyway가 비어있는 데이터베이스와 연결되었다고 생각하자.



Flyway는 DB 버전 관리를 위해 flyway_schema_history라는 테이블을 사용한다.

처음에 flyway_schema_history 테이블을 찾고, 비어있는 데이터베이스이기 때문에 해당 테이블이 없으므로 이를 찾지 않고 대신 생성한다.

이제 우리의 DB는 flyway_schema_history라는 하나의 빈 테이블이 있는 모습이 되었다.


비어있는 DB

flyway_schema_history를 생성


flyway_schema_history 테이블은 데이터베이스의 상태를 추적하는데 사용된다.

Flyway는 마이그레이션을 위해 SQL 또는 Java로 작성된 파일을 스캔한다.

스크립트 파일은 /resources/db/migration 디렉토리에 적어주면 된다.

여기서 마이그레이션이란, 일련의 변경 단위를 의미한다.
ex) 테이블이 1개 생성되고, 2개 수정되는 것이 한 묶음일 경우 이는 하나의 마이그레이션으로 본다.
마이그레이션은 버전 번호를 기준으로 정렬되어 순서대로 적용된다.

숫자가 작은 버전부터 큰 버전 순서대로 실행된다.

* 버전은 정수로 인식되므로 3.10과 3.2 중 3.2가 먼저 실행된다는 것을 주의하자.


각 마이그레이션이 적용되면, schema history 테이블에도 마이그레이션 내역이 함께 기록된다.


Flyway에는 Versioned / Undo / Repeatable Migration 3 종류가 있다.

Versioned Migrations
Flyway의 핵심 기능으로, 마이그레이션 스크립트의 최신 버전과 현재 데이터베이스의 스키마 버전을 비교하고, 차이점이 있다면 마이그레이션 스크립트를 순차적으로 실행하여 최신 스키마와 격차를 좁혀 나간다.



마이그레이션 버전이 1부터 9까지 있고, 현재 개발 환경의 데이터베이스 스키마가 5 버전이라면, 최신 버전에 대해 마이그레이션을 실행하면 6~9 버전의 마이그레이션 스크립트가 순차적으로 실행된다.



만악 개발 환경의 데이터베이스 스키마가 9 버전이라면 마이그레이션 스크립트와 차이점이 없으므로 아무 일도 발생하지 않는다.

Undo Migrations
Flyway의 유료 버전에서만 사용 가능하다.

가장 최근에 적용된 Versioned Migration을 실행 취소한다.

Repeatable Migrations
모든 마이그레이션 스크립트가 실행된 이후 실행되는 스크립트이다.

Repeatable Migrations끼리는 description 순서대로 실행된다.

한번 실행되며, 파일이 변경되어 체크섬이 변경되면 또 실행된다.


---

**라고하는데 직접 해보고 다시보자....!**

---


# 의존성 추가
```shell
# MySQL 8.X 버전이거나, MariaDB를 사용하는 경우
implementation 'org.flywaydb:flyway-mysql'

# 그 외 경우
implementation 'org.flywaydb:flyway-core'
```

# JPA 설정
application.yml에서 ddl-auto 옵션을 설정해주자.

```shell
# dev
 spring:
  jpa:
    hibernate:
      ddl-auto: validate
 
# prod
 spring:
  jpa:
    hibernate:
      ddl-auto: none
```

ddl-auto 옵션 validate :

데이터베이스 스키마와 JPA 엔티티 구조가 같은지 비교하고,
다를 경우 어플리케이션을 실행하지 못하도록 한다.

개발 환경은 validate, 운영 환경은 none으로 설정함.

# Flyway 활성화
```shell
spring:
  flyway:
    enabled: true
    baseline-on-migrate: true
```
H2 db와 ddl=auto를 create로 사용하는 로컬에선 flyway를 사용하지 않을거라


application-local.yml 에선 flyway.enabled: false 로 했고,


application-dev.yml 과 application-prod.yml에서는 true로 작성해주었다.

그리고 기존 테이블 정보와 데이터만 있는 경우
baseline-on-migrate. true로 해줌.

baseline은 flyway의 기준을 설정해주는 명령어로,
테이블이 없는 경우 생성해주는 옵션이다.


#  resources/db/migration에 .sql 파일 추가
파일명 지을때 아래와 같은 형식이 있다.

```js
Prefix - (V, U, R)
V: Verion, U: undo, R: Repeatable
Version - 버전 정보(정수, 소수, 날짜 등...)
Seperator - __ (underscore 2개)
Description - 설명
```

`V1__init_tables.sql`
이렇게 지어줬다.

