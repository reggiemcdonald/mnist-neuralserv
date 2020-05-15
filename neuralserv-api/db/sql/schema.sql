CREATE TABLE IF NOT EXISTS privilege (
    id bigint primary key,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS role (
    id bigint primary key,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE  IF NOT EXISTS app_user (
    id bigint primary key,
    username text UNIQUE NOT NULL,
    password text NOT NULL
);

CREATE TABLE IF NOT EXISTS role_privilege (
    role_id bigint,
    privilege_id bigint,
    primary key (role_id, privilege_id),
    foreign key (role_id) references role(id) ON DELETE CASCADE,
    foreign key (privilege_id) references privilege(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS app_user_role (
    role_id bigint,
    user_id bigint,
    primary key (role_id, user_id),
    foreign key (role_id) references role(id) on delete cascade,
    foreign key (user_id) references app_user(id) on delete cascade
);

CREATE TABLE number_image_entity (
    id bigint primary key,
    session_id bigint not null,
    label integer not null,
    expected_label integer,
    image_weights double precision[]
);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence;
