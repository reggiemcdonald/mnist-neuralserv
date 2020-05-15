INSERT INTO role
    VALUES(1, 'ROLE_USER');
INSERT INTO role
    VALUES (2, 'ROLE_ADMIN');
INSERT INTO privilege
    VALUES (1, 'PRIVILEGE_VIEW');
INSERT INTO privilege
    VALUES (2, 'PRIVILEGE_EDIT');
INSERT INTO privilege
    VALUES (3, 'PRIVILEGE_MAINTAIN');
INSERT INTO role_privilege
    VALUES (1, 1),
           (1, 2),
           (2, 1),
           (2, 2),
           (2, 3);
