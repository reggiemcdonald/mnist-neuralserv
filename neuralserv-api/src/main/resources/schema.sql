DROP SEQUENCE IF EXISTS hibernate_sequence;
DROP TABLE IF EXISTS number_image_entity, matrix_row_entity, training_session;

CREATE TABLE number_image_entity (
    id bigint primary key,
    session_id bigint not null,
    label integer not null,
    expected_label integer,
    image_weights double precision[][]
);
CREATE TABLE training_session (
    id bigint primary key,
    internal_training_size integer not null,
    external_training_size integer not null,
    internal_number_correct integer not null,
    external_number_correct integer not null,
    training_date timestamp not null
);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;