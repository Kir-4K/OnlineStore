/*DDL*/
-- CREATE DATABASE online_store_repository encoding ='UTF8';
-- SET SEARCH_PATH = online_store_repository;
-- CREATE SCHEMA online_store;

CREATE TABLE online_store.category
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE online_store.product
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64)      NOT NULL UNIQUE,
    category_id BIGINT           NOT NULL REFERENCES online_store.category (id) ON DELETE CASCADE ON UPDATE CASCADE,
    price       DOUBLE PRECISION NOT NULL,
    number      INTEGER          DEFAULT 0,
    rating      DOUBLE PRECISION DEFAULT 0,
    description VARCHAR(1024)
);

CREATE TABLE online_store.user
(
    id       BIGSERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    role     VARCHAR(16) NOT NULL
);

CREATE TABLE online_store.address
(
    id        BIGSERIAL PRIMARY KEY,
    city      VARCHAR(32),
    street    VARCHAR(64),
    house     VARCHAR(4),
    apartment VARCHAR(4)
);

CREATE TABLE online_store.customer
(
    id         BIGSERIAL PRIMARY KEY,
    last_name  VARCHAR(32),
    first_name VARCHAR(32)        NOT NULL,
    mail       VARCHAR(64) UNIQUE,
    phone      VARCHAR(16) UNIQUE NOT NULL,
    user_id    BIGINT UNIQUE REFERENCES online_store.user (id) ON DELETE CASCADE ON UPDATE CASCADE,
    address_id BIGINT UNIQUE REFERENCES online_store.address (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE online_store.online_order
(
    id          BIGSERIAL PRIMARY KEY,
    customer_id BIGINT REFERENCES online_store.customer (id) ON DELETE CASCADE ON UPDATE CASCADE,
    payment     VARCHAR(16),
    date        TIMESTAMP NOT NULL,
    status      VARCHAR(16)
);

CREATE TABLE online_store.product_order
(
    online_order_id BIGINT REFERENCES online_store.online_order (id) ON DELETE CASCADE ON UPDATE CASCADE,
    product_id      BIGINT REFERENCES online_store.product (id) ON DELETE CASCADE ON UPDATE CASCADE,
    quantity        INTEGER NOT NULL,
    PRIMARY KEY (online_order_id, product_id)
);

CREATE TABLE online_store.news
(
    id      BIGSERIAL PRIMARY KEY,
    date    TIMESTAMP(0) NOT NULL,
    title   VARCHAR(256) NOT NULL UNIQUE,
    text    TEXT         NOT NULL,
    user_id BIGINT REFERENCES online_store.user (id) ON DELETE CASCADE ON UPDATE CASCADE
);
