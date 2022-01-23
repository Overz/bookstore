CREATE TABLE book
(
    cdBook        TEXT   NOT NULL,
    nmAuthor      TEXT   NOT NULL,
    nmBook        TEXT   NOT NULL,
    deDescription TEXT   NOT NULL,
    dtCreated     BIGINT NOT NULL,

    CONSTRAINT pk_book PRIMARY KEY (cdBook)
);

CREATE INDEX ix_book_nmauthor ON book (nmAuthor);
CREATE INDEX ix_book_nmbook ON book (nmBook);
CREATE INDEX ix_book_dedescription ON book (deDescription);