create schema if not exists "mjis";

create table if not exists "mjis".users
(
    id          bigint generated always as identity,
    name        character varying(256)  not null,
    email       character varying(1024) not null,
    profile_url character varying(1024) not null,
    created_at  timestamp               not null default current_timestamp,
    updated_at  timestamp               not null default current_timestamp,

    primary key (id)
);

create table if not exists "mjis".pins
(
    id            bigint generated always as identity,
    name          character varying(256)  default null,
    description   character varying(1024) default null,
    resource_url  character varying(1024) default null,
    thumbnail_url character varying(1024) default null,
    created_at    timestamp not null      default current_timestamp,
    created_by_id bigint    not null,
    updated_at    timestamp not null      default current_timestamp,

    primary key (id)
);

create table if not exists "mjis".boards
(
    id            bigint generated always as identity,
    name          character varying(256)          default null,
    created_at    timestamp              not null default current_timestamp,
    created_by_id character varying(255) not null,
    updated_at    timestamp              not null default current_timestamp,

    primary key (id)
);

create table if not exists "mjis".board_pin_mappers
(
    id            bigint generated always as identity,
    board_id      bigint                 not null,
    pin_id        bigint                 not null,
    created_at    timestamp              not null default current_timestamp,
    created_by_id character varying(255) not null,
    updated_at    timestamp              not null default current_timestamp,

    primary key (id),
    constraint board_pin_mappers_uq_1 unique (board_id, pin_id)
);