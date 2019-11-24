CREATE SEQUENCE app_notification_subscriptions_SEQ
    MINVALUE 1
    START WITH 1000
    INCREMENT BY 1;


CREATE TABLE app_notification_subscriptions
(
    id           BIGINT NOT NULL PRIMARY KEY,
    uuid         TEXT   NOT NULL,
    subscription TEXT   NOT NULL
);
