/* JPA가 Entity는 알아서 다 해줬음. 이건 테스트용.*/
CREATE TABLE IF NOT EXISTS board (
                                     idx BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     title VARCHAR(255),
                                     contents VARCHAR(255),
                                     author VARCHAR(255),
                                     created_at TIMESTAMP
);
