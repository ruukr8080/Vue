```java
        long total = query.stream().count(); // 여기서 전체 카운팅 다 한 다음 아래 에서 조건 작업. [스트림 말고 딴거쓰면 안댐?]

        List<BoardEntity> results = query
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardEntity.idx.desc())
                .fetch();
        return new PageImpl<>(results, pageable, total);

```
org.hibernate.resource.jdbc.internal.LogicalConnectionManagedImpl@3587609b is closed

이거로 하니까 트렌잭션 오류 뜸.

long total = query.stream().count();
return new PageImpl<>(results, pageable, total);

에서

 

return PageableExecutionUtils.getPage(results, pageable, query::fetchCount);

이거로 바꾸니까 해결됨.

```javascript
public Page<BoardEntity> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {
    JPAQuery<BoardEntity> query = queryFactory.selectFrom(boardEntity);

    BooleanExpression searchKeyword = searchKeywords(searchCondition.getSk(), searchCondition.getSv());
    if (searchKeyword != null) {
        query.where(searchKeyword);
    }

    List<BoardEntity> results = query
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(boardEntity.idx.desc())
            .fetch();

    return PageableExecutionUtils.getPage(results, pageable, query::fetchCount);
}


```
이렇게 하면 되는거라는데,
if (searchKeyword != null) {
query.where(searchKeyword);
}
이부분이 핵심인듯하다.


PageableExecutionUtils.getPage() 메서드를 사용하면
long total = query.stream().count(); 이렇게 안해도
total 값을 자동으로 계산해준다고 한다. 
이렇게 하면 별도의 카운트 쿼리를 실행하지 않아도 되므로 성능 향상에 도움이 될 수 있습니다.

앞으론 쿼리 전체 카운팅 하는거는 query::fetchCount 쓰면 되겠다.