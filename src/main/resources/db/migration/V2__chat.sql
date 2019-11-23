CREATE TYPE CHATUSER AS ENUM ('NEEDY', 'VOLUNTEER');

CREATE SEQUENCE conversation_SEQ
    MINVALUE 1
    START WITH 1000
    INCREMENT BY 1;


CREATE TABLE conversation
(
    id           BIGINT       NOT NULL PRIMARY KEY,
    needy_id     VARCHAR(255) NOT NULL,
    volunteer_id VARCHAR(255) NOT NULL
);

CREATE SEQUENCE message_SEQ
    MINVALUE 1
    START WITH 1000
    INCREMENT BY 1;


CREATE TABLE message
(
    id               BIGINT   NOT NULL PRIMARY KEY,
    owner            CHATUSER NOT NULL,
    client_timestamp BIGINT   NOT NULL,
    content          TEXT     NOT NULL,
    conversation_id  BIGINT REFERENCES conversation (id) ON DELETE CASCADE
);

