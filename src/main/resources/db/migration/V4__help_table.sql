CREATE TYPE TIMEBOX AS ENUM ('URGENT', 'NOW', 'HOURS', 'DAY', 'NOT_IMPORTANT');
CREATE TYPE CATEGORY AS ENUM ('TRANSPORT', 'COMMUNICATION_PROBLEMS', 'PHYSICAL_HELP', 'SUPPORT', 'OTHER');
CREATE TYPE STATE AS ENUM ('NEW', 'PENDING', 'RESERVED', 'CLOSED');

CREATE SEQUENCE help_request_seq
    MINVALUE 1
    START WITH 1000
    INCREMENT BY 1;

CREATE TABLE help_request
(
    id                 BIGINT       NOT NULL PRIMARY KEY,
    needy_id           VARCHAR(255) NOT NULL,
    helper_id          VARCHAR(255),
    needy_disabilities TEXT,
    localization       VARCHAR(255),
    time_box           TIMEBOX      NOT NULL,
    category           CATEGORY     NOT NULL,
    description        TEXT,
    state              STATE        NOT NULL
);