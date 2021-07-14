CREATE TABLE batch_status (
    id SERIAL PRIMARY KEY,
    batch_date DATE,
    batch_key VARCHAR(25),
    status VARCHAR(10)
);
