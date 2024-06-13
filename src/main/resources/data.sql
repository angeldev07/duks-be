-- -------------------------------------------------------------------------------------------------------------------------
-- Drop tables before

DROP TABLE IF EXISTS orders_x_products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS stock;
DROP TABLE IF EXISTS bills;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS clients;

-- --------------------------------------------------------------------------------------------------------------------------

-- Create product table.
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    base_price DOUBLE,
    amount INT,
    low_stock BOOLEAN,
    active BOOLEAN,
    sell BOOLEAN,
    available BOOLEAN,
    profile_img LONGTEXT,
    delete_flag BOOLEAN,
    iva INT
);

-- Create category table.
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    active BOOLEAN,
    delete_flag BOOLEAN
);

-- Create Order table.
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE
);

-- Create Bill table.
CREATE TABLE IF NOT EXISTS bills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_price DOUBLE,
    date_bill DATE
);

-- Create Client table.
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(100) UNIQUE,
    card_id VARCHAR(20) UNIQUE,
    gender CHAR(1),
    birth_day DATE,
    active BOOLEAN,
    last_visit DATE,
    address VARCHAR(255),
    phone VARCHAR(255),
    delete_flag BOOLEAN
);

-- Create User table.
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    rol VARCHAR(255)
);

-- ---------------------------------------------------------------------------------------------------------------------------

-- ----------------------------- CONSTRAINT ABOUT FOREIGN KEY -------------------------------------

-- FOREIGN KEY product with stock.
ALTER TABLE products
    ADD COLUMN category_id INT,
    ADD CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (id);

-- FOREIGN KEY order with users, bill and client.
ALTER TABLE orders
    ADD COLUMN user_id INT NOT NULL,
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    ADD COLUMN client_id INT NOT NULL,
    ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id),
    ADD COLUMN bill_id INT NOT NULL,
    ADD CONSTRAINT fk_bill_id FOREIGN KEY (bill_id) REFERENCES bills (id);

-- ------------------------------------------------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS orders_x_products (
   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   product_id INT,
   order_id INT,
   amount INT,
   FOREIGN KEY (product_id) REFERENCES products(id),
   FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- ----------------------------------------------------------------------------------------------------------------------------------

-- ----------------------------- INSERT VALUES  -------------------------------------
-- Insert data in users table
INSERT INTO users (first_name, last_name, email, password, rol)
VALUES
('John', 'Doe' , 'admin@example.com', '$2a$10$1WKr48i17yaW47oNUXB6SOHfJURWMiwTYioyfSA46IMn8SDQPGgLa', 'ROLE_ADMIN'),
('Janne', 'Doe' , 'jannedoe@example.com', '$2a$10$1WKr48i17yaW47oNUXB6SOHfJURWMiwTYioyfSA46IMn8SDQPGgLa', 'ROLE_EMPLOYEE');

-- Insert data in clients table
INSERT INTO clients (name, last_name, email, card_id, gender, birth_day, active, last_visit, address, phone,delete_flag)
VALUES
('Juan', 'Perez', 'juan@example.com', '1005879563', 'M', '1990-01-15', true, '2023-11-21', 'Calle 123', '123-456-7890',0),
('Maria', 'Gomez', 'maria@example.com', '1007346589', 'F', '1985-05-10', true, '2023-11-20', 'Avenida 456', '987-654-3210',0);

-- Insert data in categories table
INSERT INTO categories (name,active, delete_flag)
VALUES
    ('Bebidas',1 , 0),
    ('Cafes', 1 ,0);

-- Insert data in products table
INSERT INTO products (name, base_price, amount, low_stock, active, sell, available, delete_flag, category_id, iva)
VALUES
('Cafe con leche', 2500, 50, false, true, true, true, false,1, 5),
('Capucchino', 3500, 30, false, true, true, true, false,1, 5);

-- Insert data in bills table
INSERT INTO bills (total_price, date_bill)
VALUES
(13125.00, '2023-11-21'),
(11025.00, '2023-11-20');

-- Insert data in orders table
INSERT INTO orders (date, user_id, client_id, bill_id)
VALUES
('2023-11-21', 1, 1, 1),
('2023-11-20', 2, 2, 2);

-- Insert data in orders_x_products table
INSERT INTO orders_x_products (product_id, order_id, amount)
VALUES
(1, 1, 5),
(2, 2, 3);



