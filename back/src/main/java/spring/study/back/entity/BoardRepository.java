package spring.study.back.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/*
* 디비에 접근하기 위한 클래슨데 인터페이스로 만듬
* entity 쓰고 있으니깐 Jpa 상속 받아줘야됨.
* 근데 구현체 없어도 되나?
*/

// 페이징 작업 시작!
// 이 전엔 게시글 목록을 화면으로 보낼때 리스트에만 보냈는데, 헤더[데이터랑 페이징 정보 들음] 객체 안에 담아서 보내보자!
// 이를 위해 모델 패키지 만들어서 -> Header<T> / pagination 클래스 생성 ->  BoardRepository에 Page로 리턴하는 쿼리 메소드를 추가.
// 그리고 생성한 쿼리 메서드 쓸거니까 서비스랑 리스트 내용 수정. getBoardList() 호출하면 Pageble 객체를 통해 쿼리메서드 호출해서 데이터 가져올거임.
// 이건  JPA에서 제공하는 페이징처리 기능임.


//Pageable 에서 리스트 인덱싱을 할때 0부터 시작함. 1페이지 버튼을 눌렀지만 Pageable에는 0으로 입력되어야 됨.
//그러나 매번 처리하기에는 복잡하므로 Config 클래스를 생성해서 작업해야댐.
//WebMvcConfig.java 클래스 생성.

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    /**
     * @param pageable
     *
     * @return pagination
     */
    Page<BoardEntity> findAllByOrderByIdxDesc(Pageable pageable);

}
