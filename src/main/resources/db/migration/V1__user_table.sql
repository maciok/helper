CREATE SEQUENCE app_user_SEQ
  MINVALUE 1
  START WITH 1000
  INCREMENT BY 1;


CREATE TABLE app_user
(
  id BIGINT NOT NULL PRIMARY KEY,
  uuid VARCHAR (255) NOT NULL
);
