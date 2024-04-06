CREATE TABLE IF NOT EXISTS brand
(
    id BIGINT PRIMARY KEY,
    vehicle_id BIGINT,
    name VARCHAR(255),
    type VARCHAR(255),
    count_of_seats INT,
    capacity INT,
    tank NUMERIC
);

INSERT INTO brand (id, vehicle_id, name, type, count_of_seats, capacity, tank)
VALUES (1, 1, 'Tesla Model Y', 'PASSENGER', 5, 500, null),
       (2, 2, 'BMW X6', 'PASSENGER', 5, 550, 200),
       (3, 3, 'Maybach', 'PASSENGER', 5, 600, 200);