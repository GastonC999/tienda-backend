CREATE TABLE hero_slides (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    cta VARCHAR(100),
    href VARCHAR(255),
    image_url VARCHAR(500),
    orden INT NOT NULL
);