DROP TABLE IF EXISTS memos;

CREATE TABLE memos (
    memo_id serial  PRIMARY KEY,
    subject text NOT NULL
);

INSERT INTO
    memos (subject)
VALUES 
    ('sample1') ,
    ('sample2') ,
    ('sample3') ;
