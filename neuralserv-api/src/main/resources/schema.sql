DROP TABLE IF EXISTS number_image, training_session;
CREATE TABLE number_image (
    id serial primary key,
    session_id integer not null,
    label integer not null,
    expected_label integer,
    image_weights double precision[][]
);
CREATE TABLE training_session (
    id serial primary key,
    internal_training_size integer not null,
    external_training_size integer not null,
    internal_number_correct integer not null,
    external_number_correct integer not null,
    training_date timestamp not null
);