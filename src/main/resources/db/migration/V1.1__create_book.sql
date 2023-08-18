CREATE TABLE book
(
    id          serial NOT NULL,
    description varchar(255) DEFAULT NULL,
    title       varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
);