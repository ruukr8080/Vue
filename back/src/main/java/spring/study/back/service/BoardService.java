package spring.study.back.service;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.study.back.entity.BoardEntity;
import spring.study.back.entity.BoardRepository;
import spring.study.back.entity.BoardRepositoryCustom;
import spring.study.back.model.Header;
import spring.study.back.model.Pagination;
import spring.study.back.model.SearchCondition;
import spring.study.back.web.dtos.BoardDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Tag(name = "service")
public class BoardService {

    @Autowired
    private BoardRepository bodRepo;

    @Autowired
    private BoardRepositoryCustom bodRepoC;

    //GET 게시글 목록 가져옴(목록 가져오기,페이징 목록 가져오기,검색 목록 가져오기)
    /* ⭐ 검색조건으로 목록 가져오기 */
    public Header<List<BoardDto>> getBoardList(Pageable pageable, SearchCondition searchCondition) {
        List<BoardDto> dtos = new ArrayList<>();
        Page<BoardEntity> bodEs = bodRepoC.findAllBySearchCondition(pageable, searchCondition);
        for (BoardEntity e : bodEs) {
            BoardDto dto = BoardDto.builder()
                    .idx(e.getIdx())
                    .author(e.getAuthor())
                    .title(e.getTitle())
                    .contents(e.getContents())
                    .createdAt(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();
            dtos.add(dto);
        }
        Pagination pagination = new Pagination(
                (int) bodEs.getTotalElements()
                , pageable.getPageNumber() + 1
                , pageable.getPageSize()
                , 10
        );
        return Header.OK(dtos, pagination);
    }

    //GET 게시글만
    public BoardDto getBoard(Long id) {
        BoardEntity bodE = bodRepo.findById(id).orElseThrow(() -> new RuntimeException("게시글 없음"));
        return BoardDto.builder()
                .idx(bodE.getIdx())
                .title(bodE.getTitle())
                .contents(bodE.getContents())
                .author(bodE.getAuthor())
                .createdAt(bodE.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
        //인자로 받은 id를 Repository에서 찾아보고 있으면 그걸 또 entity에 박고선 그걸 또 dto로 변환함.
    }

    // create
    public BoardEntity create(BoardDto bodD) {
        BoardEntity bodE = BoardEntity.builder()
                .title(bodD.getTitle())
                .contents(bodD.getContents())
                .author(bodD.getAuthor())
                .createdAt(LocalDateTime.now())
                .build();
        return bodRepo.save(bodE);
        //dto를 인자로 받음. 그걸 entity에 박고선 디비에 접근해서 갖다 박음.
        //디비에 직접적으로 연결된건 entity라 dto에 있는걸 또 엔티티에 전달해줘야 디비에 박을 수 있음.

    }

    // 수정
    public BoardEntity update(BoardDto bodD) {
        BoardEntity bodE = bodRepo.findById(bodD.getIdx()).orElseThrow(() -> new RuntimeException("게시글 없음"));
        bodE.setTitle(bodD.getTitle());
        bodE.setContents(bodD.getContents());
        bodE.setAuthor(bodD.getAuthor());
        bodE.setCreatedAt(LocalDateTime.now()); //수정일 자동업뎃
        return bodRepo.save(bodE);
        //인자로 받은 Dto idx로 엔티티 뒤짐. 그래서 해당 id를 가진 엔티티를 찾아내면 필드값들을 재할당함. 그다음 Repo에 그 entity를 저장.
    }

    //삭제
    public void delete(Long id) {
        BoardEntity bodE = bodRepo.findById(id).orElseThrow(() -> new RuntimeException("게시글 없음"));
        bodRepo.delete(bodE);
        //dto에서 받아와야되지않나 얘도?
        //여튼 파라미터 id 가져와선 바로 레포 뒤져서 엔티티에다 줌. 그 담 레포에서 삭제.

    }
}


/* ⭐ 목록 가져오기 */
//    public List<BoardDto> getBoardList() {
//        List<BoardEntity> bodEntityList = bodRepo.findAll();
//        List<BoardDto> bodDtoList = new ArrayList<>();
//
//        for (BoardEntity entity : bodEntityList) {      //jpa 엔티티에 담아뒀던거 dto에 다 갖다 박음. createdAt은 가공함.
//            BoardDto bod = BoardDto.builder()
//                    .idx(entity.getIdx())
//                    .author(entity.getAuthor())
//                    .title(entity.getTitle())
//                    .contents(entity.getContents())
//                    .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
//                    .build();
//            bodDtoList.add(bod);
//        }
//        return bodDtoList;
//        //Entity 꺼내와서 dto로 변환해서 리스트에 담는다
//        //Entity를 DTO로 변환하는 이유는 JPA entity가 디비에 직접 연결돼있으니까 그 엔티티를 외부에 노출시키기 싫기 때문임(보안상).
//        //DTO는 데이터 전달을 위해 있는 객체라 Entity와 달리 필요한 데이터만 담을 수 있음
//    }

/* ⭐ 페이징 된 목록 가져오기 */
//    public Header<List<BoardDto>> getBoardList(Pageable pageable) {
//        List<BoardDto> dtos = new ArrayList<>();
//        Page<BoardEntity> bodEs = bodRepo.findAllByOrderByIdxDesc(pageable);
//        for (BoardEntity e : bodEs) {
//            BoardDto dto = BoardDto.builder()
//                    .idx(e.getIdx())
//                    .author(e.getAuthor())
//                    .title(e.getTitle())
//                    .contents(e.getContents())
//                    .createdAt(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
//                    .build();
//            dtos.add(dto);
//        }