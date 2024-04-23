CREATE TABLE IF NOT EXISTS driver
(
    id BIGINT PRIMARY KEY,
    enterprise_id BIGINT,
    active BOOLEAN,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS enterprise
(
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    city VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS vehicle_driver (
    vehicle_id BIGINT NOT NULL,
    driver_id BIGINT NOT NULL,
    PRIMARY KEY (vehicle_id, driver_id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id) ON DELETE CASCADE,
    FOREIGN KEY (driver_id) REFERENCES driver(id) ON DELETE CASCADE
);

ALTER TABLE vehicle
    ADD COLUMN IF NOT EXISTS enterprise_id BIGINT;

ALTER TABLE vehicle
    ADD COLUMN IF NOT EXISTS active_driver_id BIGINT;

ALTER TABLE vehicle
ADD CONSTRAINT fk_vehicle_enterprise_id
FOREIGN KEY (enterprise_id)
REFERENCES enterprise(id);

ALTER TABLE driver
ADD CONSTRAINT fk_driver_enterprise_id
FOREIGN KEY (enterprise_id)
REFERENCES enterprise(id);

INSERT INTO enterprise (id, name, city)
VALUES (1, 'Lockheed Martin', 'Washington'),
       (2, 'SpaceX', 'New York'),
       (3, 'NetCracker', 'Магадан');

INSERT INTO driver (id, enterprise_id, active, first_name, last_name, birthday)
VALUES (1, 1, false, 'John', 'Doe', '1990-05-15'),
       (2, 1, false, 'Alice', 'Smith', '1985-10-20'),
       (3, 2, false, 'Bob', 'Johnson', '1988-03-12'),
       (4, 2, false, 'Emily', 'Brown', '1992-07-28'),
       (5, 3, false, 'Michael', 'Davis', '1983-12-05');

UPDATE vehicle SET enterprise_id = 1 WHERE id = 1;
UPDATE vehicle SET enterprise_id = 1 WHERE id = 2;
UPDATE vehicle SET enterprise_id = 2 WHERE id = 3;
UPDATE vehicle SET enterprise_id = 2 WHERE id = 4;
UPDATE vehicle SET enterprise_id = 3 WHERE id = 5;

INSERT INTO vehicle_driver (vehicle_id, driver_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (2, 1),
       (2, 5),
       (3, 2),
       (4, 2),
       (5, 3),
       (5, 4);