drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1000 increment by 1;

insert into category (ID_CATEGORY, NAME) values (1, 'Electronics');
insert into category (ID_CATEGORY, NAME) values (2, 'Clothing & Apparel');
insert into category (ID_CATEGORY, NAME) values (3, 'Home & Kitchen');

-- Inserting 30 products into the PRODUCT table
INSERT INTO PRODUCT (id, name, price, id_category, image) VALUES
-- Electronics Category (id_category = 1)
(1, 'Smartphone', 599.99, 1, 'https://dummyurl.com/smartphone.jpg'),
(2, 'Laptop', 999.99, 1, 'https://dummyurl.com/laptop.jpg'),
(3, 'Wireless Earbuds', 79.99, 1, 'https://dummyurl.com/earbuds.jpg'),
(4, 'Smart Watch', 149.99, 1, 'https://dummyurl.com/smartwatch.jpg'),
(5, 'Bluetooth Speaker', 49.99, 1, 'https://dummyurl.com/speaker.jpg'),

-- Clothing & Apparel Category (id_category = 2)
(6, 'T-shirt', 19.99, 2, 'https://dummyurl.com/tshirt.jpg'),
(7, 'Jeans', 39.99, 2, 'https://dummyurl.com/jeans.jpg'),
(8, 'Sneakers', 59.99, 2, 'https://dummyurl.com/sneakers.jpg'),
(9, 'Dress', 49.99, 2, 'https://dummyurl.com/dress.jpg'),
(10, 'Jacket', 79.99, 2, 'https://dummyurl.com/jacket.jpg'),

-- Home & Kitchen Category (id_category = 3)
(11, 'Coffee Maker', 89.99, 3, 'https://dummyurl.com/coffeemaker.jpg'),
(12, 'Blender', 69.99, 3, 'https://dummyurl.com/blender.jpg'),
(13, 'Cookware Set', 129.99, 3, 'https://dummyurl.com/cookwareset.jpg'),
(14, 'Air Fryer', 79.99, 3, 'https://dummyurl.com/airfryer.jpg'),
(15, 'Robot Vacuum Cleaner', 199.99, 3, 'https://dummyurl.com/robotvacuum.jpg'),

-- Electronics Category (id_category = 1)
(16, 'Tablet', 299.99, 1, 'https://dummyurl.com/tablet.jpg'),
(17, 'Gaming Console', 399.99, 1, 'https://dummyurl.com/console.jpg'),
(18, 'Headphones', 129.99, 1, 'https://dummyurl.com/headphones.jpg'),
(19, 'TV', 699.99, 1, 'https://dummyurl.com/tv.jpg'),
(20, 'Digital Camera', 499.99, 1, 'https://dummyurl.com/camera.jpg'),

-- Clothing & Apparel Category (id_category = 2)
(21, 'Sweater', 29.99, 2, 'https://dummyurl.com/sweater.jpg'),
(22, 'Skirt', 24.99, 2, 'https://dummyurl.com/skirt.jpg'),
(23, 'Polo Shirt', 22.99, 2, 'https://dummyurl.com/poloshirt.jpg'),
(24, 'Leggings', 19.99, 2, 'https://dummyurl.com/leggings.jpg'),
(25, 'Hoodie', 34.99, 2, 'https://dummyurl.com/hoodie.jpg'),

-- Home & Kitchen Category (id_category = 3)
(26, 'Toaster', 39.99, 3, 'https://dummyurl.com/toaster.jpg'),
(27, 'Rice Cooker', 49.99, 3, 'https://dummyurl.com/ricecooker.jpg'),
(28, 'Electric Kettle', 29.99, 3, 'https://dummyurl.com/electrickettle.jpg'),
(29, 'Food Processor', 59.99, 3, 'https://dummyurl.com/foodprocessor.jpg'),
(30, 'Juicer', 49.99, 3, 'https://dummyurl.com/juicer.jpg');

-- Contraseña: Admin1
insert into user_entity (full_name, email, username, password, avatar, created_at, last_password_change_at) 
values ('Admin admin', 'admin@ecommerce.com','admin','$2a$10$vPaqZvZkz6jhb7U7k/V/v.5vprfNdOnh4sxi/qpPRkYTzPmFlI9p2','https://api.adorable.io/avatars/285/admin@ecommerce.com.png',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into USER_ROLES (user_id, roles) values (1,'USER');
insert into USER_ROLES (user_id, roles) values (1,'ADMIN');


-- Contraseña: UsuarioPrimero1
insert into user_entity (full_name, email, username, password, avatar, created_at, last_password_change_at) 
values ('Usuario Primero', 'usuario.primero@ecommerce.net','usuarioprimero','$2a$10$udJnz9otIl5h59sEE2dZcu4Tq/uUViihXr61ZDGeuaYEMfOHL08UG','https://api.adorable.io/avatars/285/usuario.primero@ecommerce.com.png',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into USER_ROLES (user_id, roles) values (2,'USER');

-- Contraseña: JuanaRodriguez1
insert into user_entity (full_name, email, username, password, avatar, created_at, last_password_change_at) 
values ('Juana Rodríguez', 'juana.rodriguez@ecommerce.net','juanarodriguez','$2a$10$IWrimuL74lpIzDq45xJOguEMEMJWFLwTwYPjX2JhPPg7Z5QIbtUKy','https://api.adorable.io/avatars/285/juana.rodriguez@ecommerce.com.png',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into USER_ROLES (user_id, roles) values (3,'USER');

-- Contraseña: PeterSmith69
insert into user_entity (full_name, email, username, password, avatar, created_at, last_password_change_at) 
values ('Peter Smith', 'peter.smith@ecommerce.net','petersmith','$2a$10$YgeEdMl3gseJH3IW1g3jdufdCvt4RScu/rMxd5R4g72rHuicEEocm','https://api.adorable.io/avatars/285/peter.smith@ecommerce.com.png',CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

insert into USER_ROLES (user_id, roles) values (4,'USER');

insert into order_entity (id, client_id, date_order) values (1, 2, CURRENT_TIMESTAMP);
insert into line_order (id, product_id, price, quantity, order_id) values (1, 1, 91, 1, 1);
insert into line_order (id, product_id, price, quantity, order_id) values (2, 2, 87, 3, 1);
insert into line_order (id, product_id, price, quantity, order_id) values (3, 29, 77, 2, 1);

insert into order_entity (id, client_id, date_order) values (2, 3, CURRENT_TIMESTAMP);
insert into line_order (id, product_id, price, quantity, order_id) values (4, 19, 72, 3, 2);
insert into line_order (id, product_id, price, quantity, order_id) values (5, 28, 19, 2, 2);

insert into order_entity (id, client_id, date_order) values (3, 4, CURRENT_TIMESTAMP);
insert into line_order (id, product_id, price, quantity, order_id) values (6, 9, 25, 1, 3);
insert into line_order (id, product_id, price, quantity, order_id) values (7, 13, 38, 5, 3);
insert into line_order (id, product_id, price, quantity, order_id) values (8, 22, 72, 1, 3);

insert into batch (id, name) values (1, 'Batch 1');
insert into batch_products (batch_id, product_id) values (1, 1);
insert into batch_products (batch_id, product_id) values (1, 2);
insert into batch_products (batch_id, product_id) values (1, 3);
insert into batch_products (batch_id, product_id) values (1, 4);

insert into batch (id, name) values (2, 'Batch 2');
insert into batch_products (batch_id, product_id) values (2, 1);
insert into batch_products (batch_id, product_id) values (2, 11);
insert into batch_products (batch_id, product_id) values (2, 12);
insert into batch_products (batch_id, product_id) values (2, 13);
insert into batch_products (batch_id, product_id) values (2, 14);

insert into batch (id, name) values (3, 'Batch 3');
insert into batch_products (batch_id, product_id) values (3, 21);
insert into batch_products (batch_id, product_id) values (3, 22);
insert into batch_products (batch_id, product_id) values (3, 23);
insert into batch_products (batch_id, product_id) values (3, 24);