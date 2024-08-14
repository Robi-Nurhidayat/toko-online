CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    full_name VARCHAR(100),
    phone_number VARCHAR(20),
    address TEXT,
    profile VARCHAR(255),
    communication_sw BOOLEAN,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);
