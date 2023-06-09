drop table iF EXISTS comments CASCADE;

drop table iF EXISTS compilation CASCADE;

drop table iF EXISTS compilation_events CASCADE;

drop table iF EXISTS requests CASCADE;

drop table iF EXISTS events CASCADE;

drop table IF EXISTS users;

drop table iF EXISTS location;

drop table iF EXISTS category;

drop table iF EXISTS compilation;

CREATE SCHEMA IF NOT EXISTS schema;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(255)                            NOT NULL,
    email VARCHAR(255)                            NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS location
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    lat FLOAT,
    lon FLOAT
);

CREATE TABLE IF NOT EXISTS category
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(255)                            NOT NULL
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    annotation         VARCHAR(1000)                           NOT NULL,
    category_id        BIGINT REFERENCES category (id) on delete cascade,
    confirmed_requests BIGINT,
    created_on         TIMESTAMP                               NOT NULL,
    description        VARCHAR(1000)                           NOT NULL,
    event_date         TIMESTAMP                               NOT NULL,
    user_id            BIGINT REFERENCES users (id) on delete cascade,
    location_id        BIGINT REFERENCES location (id) on delete cascade,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  BIGINT,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN                                 NOT NULL,
    state              VARCHAR(1000)                           NOT NULL,
    title              VARCHAR(1000),
    views              BIGINT
);

CREATE TABLE IF NOT EXISTS requests
(
    id           bigint GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created      TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    event_id     bigint                                  NOT NULL REFERENCES events (id) ON DELETE CASCADE,
    requester_id bigint                                  NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status       varchar                                 NOT NULL
);

CREATE TABLE IF NOT EXISTS compilation
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    pinned   BOOLEAN,
    title    VARCHAR(255),
    event_id bigint REFERENCES events (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    event_id       bigint REFERENCES events (id),
    compilation_id bigint REFERENCES compilation (id),
    PRIMARY KEY (event_id, compilation_id)
);

CREATE TABLE IF NOT EXISTS comments
(
    id         bigint generated BY DEFAULT AS IDENTITY PRIMARY KEY,
    text       varchar(2000) NOT NULL,
    created_on timestamp     NOT NULL,
    edited_on  timestamp,
    author_id  bigint REFERENCES users (id),
    event_id   bigint REFERENCES events (id)
);