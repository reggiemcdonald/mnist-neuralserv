DROP TABLE IF EXISTS number_image;
CREATE TABLE number_image (
    id serial primary key,
    session_id integer,
    label integer not null,
    expected_label integer,
    image_weights double precision[][]
);