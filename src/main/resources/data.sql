-- Create product table.
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    basePrice DOUBLE,
    amount INT,
    lowStock BOOLEAN,
    active BOOLEAN,
    sell BOOLEAN,
    available BOOLEAN,
    deleted BOOLEAN,
);

-- Create category table.
CREATE TABLE IF NOT EXISTS categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- Create Stock table.
CREATE TABLE IF NOT EXISTS stock (
    id INT AUTO_INCREMENT PRIMARY KEY,
    amount INT,
    stock INT,
    lastUpdate DATE,
);

-- Create Order table.
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    date DATE,
);

-- Create Bill table.
CREATE TABLE IF NOT EXISTS bills (
    id INT AUTO_INCREMENT PRIMARY KEY,
    basePrice DOUBLE,
    iva BOOLEAN,
    totalPrice DOUBLE,
    discounts DOUBLE,
    billDate DATE,
);

-- Create Client table.
CREATE TABLE IF NOT EXISTS clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    cardId VARCHAR(255) UNIQUE,
    gender CHAR(1),
    birthDay DATE,
    active BOOLEAN,
    lastVisit DATE,
    address VARCHAR(255),
    phone VARCHAR(255)
);

-- Create User table.
CREATE TABLE IF NOT EXISTS UserEntity (
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(255),
    lastName VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    rol VARCHAR(255),
);


------------------------------- CONSTRAINT ABOUT FOREIGN KEY -------------------------------------

-- FOREIGN KEY product with stock.
ALTER TABLE products
    ADD COLUMN stock_id INT NOT NULL
    ADD CONSTRAINT 'fk_stock_id' FOREIGN KEY (stock_id)
    REFERENCES stock (id);

-- FOREIGN KEY order with users, bill and client.
ALTER TABLE orders
    ADD COLUMN user_id INT NOT NULL,
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id),
    ADD COLUMN client_id INT NOT NULL,
    ADD CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES clients (id),
    ADD COLUMN bill_id INT NOT NULL,
    ADD CONSTRAINT fk_bill_id FOREIGN KEY (bill_id) REFERENCES bills (id);

------------------------------- Create Tables Many to MANY  -------------------------------------
CREATE TABLE IF NOT EXISTS categories_products (
   product_id INT,
   category_id INT,
   PRIMARY KEY (product_id, category_id),
   FOREIGN KEY (product_id) REFERENCES products(id),
   FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS orders_x_products (
   id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
   product_id INT,
   category_id INT,
   amount INT,
   FOREIGN KEY (product_id) REFERENCES products(id),
   FOREIGN KEY (category_id) REFERENCES categories(id)
);

------------------------------- INSERT VALUES  -------------------------------------


