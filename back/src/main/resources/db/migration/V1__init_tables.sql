CREATE TABLE IF NOT EXISTS tb_board (
                                     idx SERIAL PRIMARY KEY,
                                     title VARCHAR(255),
                                     contents VARCHAR(255),
                                     author VARCHAR(255),
                                     created_at TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tb_user (
                         IDX BIGSERIAL PRIMARY KEY,
                         ID VARCHAR(50) UNIQUE,
                         PW VARCHAR(100),
                         NAME VARCHAR(50)
);

COMMENT ON table tb_board is '게시판 테이블';
COMMENT ON TABLE tb_user IS '유저 테이블';

INSERT INTO public.tb_board (TITLE, CONTENTS, AUTHOR, CREATED_AT)
SELECT
    'Title ' || SEQ.nextval,
    'Contents ' || SEQ.nextval,
    'Author ' || SEQ.nextval,
    CURRENT_TIMESTAMP
FROM GENERATE_SERIES(1, 100) AS SEQ;