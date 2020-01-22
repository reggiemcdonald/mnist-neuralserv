DROP SEQUENCE IF EXISTS hibernate_sequence;
-- DROP TABLE IF EXISTS number_image_entity, matrix_row_entity, training_session;

CREATE TABLE app_user (
    id bigint primary key,
    username text UNIQUE NOT NULL,
    password text NOT NULL
);

CREATE TABLE number_image_entity (
    id bigint primary key,
    session_id bigint not null,
    label integer not null,
    expected_label integer,
    image_weights double precision[]
);
CREATE TABLE training_session (
    id bigint primary key,
    internal_testing_size integer not null,
    external_testing_size integer not null,
    internal_number_correct integer not null,
    external_number_correct integer not null,
    training_date timestamp not null
);

-- CREATE TABLE IF NOT EXISTS assessment (
--     number_image_id bigint NOT NULL,
--     training_session_id bigint NOT NULL,
--     network_answer integer NOT NULL,
--     PRIMARY KEY (number_image_id, training_session_id),
--     FOREIGN KEY (number_image_id) REFERENCES number_image_entity(id) ON DELETE CASCADE,
--     FOREIGN KEY (training_session_id) REFERENCES training_session(id) ON DELETE CASCADE
-- );

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;