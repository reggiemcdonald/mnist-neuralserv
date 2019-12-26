DROP TABLE IF EXISTS number_image;
CREATE TABLE number_image (
    id serial primary key,
    label integer,
    image_weights double precision[][]
);