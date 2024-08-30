package spring.study.back.entity;



import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import spring.study.back.model.SearchCondition;

import java.util.List;

import static spring.study.back.entity.QBoardEntity.boardEntity;

@RequiredArgsConstructor
@Repository
public class BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Page<BoardEntity> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {

        JPAQuery<BoardEntity> query = queryFactory.selectFrom(boardEntity);

        BooleanExpression searchKeyword = searchKeywords(searchCondition.getSk(), searchCondition.getSv());
        if (searchKeyword != null) {
            query.where(searchKeyword);
        }

//        long total = query.stream().count(); // 여기서 전체 카운팅 다 한 다음 아래 에서 조건 작업. [스트림 말고 딴거쓰면 안댐?]

        List<BoardEntity> results = query
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(boardEntity.idx.desc())
                .fetch();
//        return new PageImpl<>(results, pageable, total);

        return PageableExecutionUtils.getPage(results, pageable, query::fetchCount);

        /* PageImpl 컨트롤 클릭해서 까보면 아래와같은 설명이 있다.
        content – the content of this page, must not be null.
        pageable – the paging information, must not be null.
        total – the total amount of items available. The total might be adapted considering the length of the content given, if it is going to be the content of the last page. This is in place to mitigate inconsistencies.
         */
          /*
        PageableExecutionUtils.getPage() 메서드를 사용하면 total 값을 자동으로 계산해줌
        이렇게 하면 별도의 카운트 쿼리를 실행하지 않아도 되므로 성능 향상에 도움됨
         */
    }

    //이거 else if 말고 걍 다 if 로 해서 스코프 바로 빠져나오게 하면 어케되ㅏㄹ까
    // 조건 하나에 안때려박고 이중으로 한 이유가 뭘까
    // 그리고 cointents도 sk로 쓴다는게 뭔가 미련해보인다. 무슨 이유일까
    private BooleanExpression searchKeywords(String sk, String sv) {
        if ("author".equals(sk) && StringUtils.hasLength(sv)) {
            return boardEntity.author.contains(sv);
        }
        if ("title".equals(sk) && StringUtils.hasLength(sv)) {
            return boardEntity.title.contains(sv);
        }
        if ("contents".equals(sk) && StringUtils.hasLength(sv)) {
            return boardEntity.contents.contains(sv);
        }
        return null;
    }
}

