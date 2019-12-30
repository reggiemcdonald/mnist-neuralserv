DROP TABLE IF EXISTS training_session;
CREATE TABLE training_session (
    id serial primary key,
    internal_training_size integer not null,
    external_training_size integer not null,
    internal_number_correct integer not null,
    external_number_correct integer not null,
    training_date timestamp not null
);