package spring.study.back.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import spring.study.back.entity.BoardEntity;
import spring.study.back.model.Header;
import spring.study.back.model.SearchCondition;
import spring.study.back.service.BoardService;
import spring.study.back.web.dtos.BoardDto;

import java.util.List;

@Slf4j
@RequestMapping("/board")
@RestController //json 형식으로 반환시켜줌
@Tag(name = "게시판 Controller")
@CrossOrigin(maxAge = 3600)
@RequiredArgsConstructor(onConstructor = @__(@Autowired)) // 굳이 싶지만 더 명시적일 수 있음
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/list")
    @Tag(name = "게시글 리스트로 조회 GetMapping", description = "게시글 전부 조회, 걍 리스트로 가져옴")
    public Header<List<BoardDto>> boardList(
            @PageableDefault(sort = {"idx"}) Pageable pageable,
            SearchCondition searchCondition
    ) {
        return boardService.getBoardList(pageable,searchCondition);
    }

    @GetMapping("/{id}")
    @Tag(name = "게시글 조회 GetMapping('{id}')", description = "특정 글 조회: 특정 id 값으로 파라미터 넘겨서 조회,조작")
    public BoardDto getBoard(@PathVariable(name = "id") Long id) {
        log.info("(어차피 프론트에서 id로 캡처떠서 id만 요청보내면 됨)요청 받은 id=={}", id);
        BoardDto result = boardService.getBoard(id);
        log.info("맞음??{}", result);
        return boardService.getBoard(id);
    }

    @PostMapping()
    @Tag(name = "게시글 작성 PostMapping", description = "글 생성: http 파라미터 정보가 필요 없어서 PostMapping으로 함. idx값 중복시 500에러. ")
    public BoardEntity create(@RequestBody BoardDto boardDto) {

        return boardService.create(boardDto);
    }
//    @PutMapping()
//    @Tag(name = "게시글 작성 PutMapping", description = "글 생성: http status 함 살펴보고 쓸지말지 결정.")
//    public ResponseEntity<BoardDto> create(@RequestBody BoardDto boardDto) {
//        return ResponseEntity.status(HttpStatus.CREATED).body(boardDto);
//    }
    @PatchMapping()
    @Tag(name = "게시글 수정 PatchMapping", description = "글 수정: 스프링부트가 제공하는 HTTP PATCH 메서드. PUT메서드로 해도되긴하는데 PATCH 메서드는 Put과 달리 수정하기로 명시한 데이터만 갱신되고 나머지는 그대로 냅둔다.")
    public BoardEntity updateBoard(@RequestBody BoardDto boardDto) {
        return boardService.update(boardDto);
    }

    @DeleteMapping("/{id}")
    @Tag(name = "게시글 삭제", description = "글 삭제: 스프링부트가 제공하는 Mapping 기능. 데이터를 삭제할 때 씀. ")
    public void deleteBoard(@PathVariable Long id) {
        boardService.delete(id);
    }
}
