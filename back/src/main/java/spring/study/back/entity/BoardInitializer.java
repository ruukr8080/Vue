package spring.study.back.entity;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BoardInitializer {

    @Autowired
    private BoardRepository bodRepo;

    @PostConstruct
    @Transactional
    public void insertInitialData() {
        if (bodRepo.count() == 0) {
            for (int i = 1; i <= 1000; i++) {
                BoardEntity board = new BoardEntity();
                board.setTitle("Title " + i);
                board.setContents("Contents " + i);
                board.setAuthor("Author " + i);
                board.setCreatedAt(LocalDateTime.now());
                bodRepo.save(board);
            }
        }
    }
}