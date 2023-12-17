CREATE TABLE IF NOT EXISTS vehicle
(
    id UUID PRIMARY KEY,
    price NUMERIC,
    release_date DATE,
    mileage FLOAT
);

INSERT INTO vehicle (id, price, release_date, mileage)
VALUES ('cf0914e7-5e02-4c28-afdb-ed5313b66918', 600000, '2023-01-01', 200),
       ('d220c225-f0bd-4907-ad1f-6a67be1b8d9d', 800000, '2022-01-01', 100),
       ('5c3d71b9-5d80-44c6-8681-64cacc944ede', 1500000, '2021-01-01', 0);