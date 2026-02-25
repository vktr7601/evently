INSERT INTO users (first_name, last_name, age, email, password, user_role, updated_at, created_at)
VALUES ('John', 'Doe', 30, 'john.doe@admin.evently.com', 'password123', 'ADMIN', now(), now()),
       ('Jane', 'Smith', 25, 'jane.smith@email.com', 'password123', 'USER', now(), now()),
       ('Carlos', 'Rivera', 34, 'carlos.rivera@email.com', 'password123', 'USER', now(), now()),
       ('Emily', 'Chen', 28, 'emily.chen@email.com', 'password123', 'USER', now(), now()),
       ('Michael', 'Brown', 42, 'michael.brown@email.com', 'password123', 'ADMIN', now(), now());

INSERT INTO follow_category (user_id, category_id, updated_at, created_at)
VALUES (1, 1, now(), now()),
       (1, 2, now(), now()),
       (2, 1, now(), now()),
       (2, 3, now(), now()),
       (3, 5, now(), now()),
       (3, 4, now(), now()),
       (4, 1, now(), now()),
       (4, 5, now(), now()),
       (5, 2, now(), now()),
       (5, 4, now(), now());