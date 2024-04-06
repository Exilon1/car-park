DROP TABLE IF EXISTS brand;
DROP TABLE IF EXISTS vehicle;
CREATE TABLE vehicle
(
    id BIGINT PRIMARY KEY,
    price NUMERIC,
    release_date DATE,
    mileage FLOAT
);

INSERT INTO vehicle (id, price, release_date, mileage)
VALUES (1, 600000, '2023-01-01', 200),
       (2, 800000, '2022-01-01', 100),
       (3, 1500000, '2021-01-01', 0);