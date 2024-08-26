package spring.study.back.model;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Tag(name="paging",description = "ddd")
public class Pagination {
    // 한 페이지에 보여지는 게시글 목록 갯수
    private int pageSize;
    int page, block, totalListCnt, totalPageCnt, totalBlockCnt, startPage, endPage, prevBlock, nextBlock, startIndex;
    //totalNum  {총게시글,총페이지,총구간}
    //페이지     {시작,현재,마지막)
    //블럭       {현재,이전,다음)
    //시작 인덱스
    public Pagination(Integer totalListCnt, Integer page, Integer pageSize, Integer blockSize) {
        this.pageSize = pageSize;

        //현재 페이지
        this.page = page;

        //총 게시글 수
        this.totalListCnt = totalListCnt;

        //총 페이지 수
        totalPageCnt = (int) Math.ceil(totalListCnt * 1.0 / this.pageSize);

        //총 블럭 수
        totalBlockCnt = (int) Math.ceil(totalPageCnt * 1.0 / blockSize);

        //현재 블럭
        block = (int) Math.ceil((this.page * 1.0) / blockSize);

        //if(block < 1) block = 1

        //블럭 시작 페이지
        startPage = ((block - 1) * blockSize + 1);

        //블럭 마지막 페이지
        endPage = startPage + blockSize - 1;

        //블럭 마지막 페이지 validation
        if (endPage > totalPageCnt) endPage = totalPageCnt;

        // 이전 블럭 (클릭 시, 이전 블럭 마지막 페이지)
        prevBlock = (block * blockSize) - blockSize;

        // 이전 블럭 validation
        if (prevBlock < 1) prevBlock = 1;

        //다음 블럭 (클릭 시, 다음 블럭 첫번째 페이지)
        nextBlock = (block * blockSize + 1);

        // 다음 블럭 validation
        if (nextBlock > totalPageCnt) nextBlock = totalPageCnt;

        //if(this.page < 1) this.page = 1

        startIndex = (this.page - 1) * this.pageSize;
    }
}

