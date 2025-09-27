INSERT INTO roles (role) VALUES ('ADMIN');
INSERT INTO roles (role) VALUES ('USER');

INSERT INTO users (first_name, last_name, email, password, enabled)
VALUES ('Jack', 'Johns', 'jack@gmail.com', '{bcrypt}$2a$12$FSFp7fWZL8N57uyFLWoHcOWneGqe5XKOFFNu83exDXL0xl2XypT3O', TRUE);

INSERT INTO users (first_name, last_name, email, password, enabled)
VALUES ('Ann', 'Smith', 'ann@gmail.com', '{noop}1234', TRUE);

-- связь users <-> roles
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1); -- John -> ADMIN
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2); -- John -> USER
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2); -- Jane -> USER
