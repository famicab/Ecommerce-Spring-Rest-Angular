CREATE TABLE IF NOT EXISTS CATEGORY(ID_CATEGORY INT PRIMARY KEY NOT NULL, NAME VARCHAR2(25), DESCRIPTION VARCHAR2(200));
INSERT INTO CATEGORY (ID_CATEGORY, NAME, DESCRIPTION) VALUES
(1, 'Electronics', 'Browse our latest selection of electronics, gadgets, and accessories.'),
(2, 'Clothing & Apparel', 'Discover trendy fashion, stylish clothing, and accessories for all occasions.'),
(3, 'Home & Kitchen', 'Find everything you need to transform your home and kitchen into a cozy paradise.');

-- Products
CREATE TABLE IF NOT EXISTS PRODUCT(ID_PRODUCT INT PRIMARY KEY NOT NULL, NAME VARCHAR2(25), DESCRIPTION VARCHAR2(200), PRICE NUMBER(9,0), ID_CATEGORY INT,
FOREIGN KEY (ID_CATEGORY) REFERENCES CATEGORY (ID_CATEGORY));

INSERT INTO PRODUCT (ID_PRODUCT, NAME, DESCRIPTION, PRICE, ID_CATEGORY) VALUES
(1, 'Smartphone', 'A powerful smartphone with cutting-edge features.', 99.99, 1),
(2, 'T-shirt', 'A comfortable cotton t-shirt in various colors and sizes.', 15.99, 2),
(3, 'Coffee Maker', 'Brew your favorite coffee with this sleek coffee maker.', 21.15, 3),
(4, 'Remote Control Car', 'A fun remote control car for kids and adults alike.', 25.49, 1);

--Users
CREATE TABLE IF NOT EXISTS USER_ENTITY(
    ID_USER BIGINT NOT NULL,
	USERNAME VARCHAR2(255),
	PASSWORD VARCHAR2(255),
	AVATAR VARCHAR2(255)
);

CREATE TABLE IF NOT EXISTS USER_ROLES(
    ID_USER BIGINT NOT NULL,
    ROLES VARCHAR(255)
);