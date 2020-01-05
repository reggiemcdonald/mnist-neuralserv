DROP SEQUENCE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS number_image_entity, matrix_row_entity, training_session;
CREATE TABLE number_image_entity (
    id bigint primary key,
    session_id bigint not null,
    label integer not null,
    expected_label integer,
    image_weights double precision[][]
);
CREATE TABLE matrix_row_entity (
    id bigint primary key,
    number_image_id bigint,
    row_number integer not null,
    row double precision[]
);

CREATE TABLE training_session (
    id serial primary key,
    internal_training_size integer not null,
    external_training_size integer not null,
    internal_number_correct integer not null,
    external_number_correct integer not null,
    training_date timestamp not null
);
CREATE TABLE test (
    id serial primary key,
    value integer
);
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;