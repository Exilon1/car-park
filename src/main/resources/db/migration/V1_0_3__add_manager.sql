CREATE TABLE IF NOT EXISTS manager
(
    id BIGINT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS enterprise_manager (
    enterprise_id BIGINT NOT NULL,
    manager_id BIGINT NOT NULL,
    PRIMARY KEY (enterprise_id, manager_id),
    FOREIGN KEY (enterprise_id) REFERENCES enterprise(id) ON DELETE CASCADE,
    FOREIGN KEY (manager_id) REFERENCES enterprise(id) ON DELETE CASCADE
);

INSERT INTO manager (id, username, password, role)
VALUES (1, 'John', '$2a$10$0cH0LpymlKJEWIU2P3bVV.eWT/k8QggX26LpkR6piJZymj7BnMPyC', 'USER'),
       (2, 'Alice', '$2a$10$0cH0LpymlKJEWIU2P3bVV.eWT/k8QggX26LpkR6piJZymj7BnMPyC', 'USER');

INSERT INTO enterprise_manager (enterprise_id, manager_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (3, 2);