CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL,
    name VARCHAR(255),
    description VARCHAR(255),
    price DOUBLE PRECISION,
    image VARCHAR(255),
    category VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGSERIAL,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    total DOUBLE PRECISION,
    status VARCHAR(255),
    created_at TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_items (
    id BIGSERIAL,
    product_id BIGINT,
    product_name VARCHAR(255),
    price DOUBLE PRECISION,
    quantity INTEGER,
    order_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES orders(id)
);