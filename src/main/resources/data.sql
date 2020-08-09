-- admin
insert into users (name, email, password, role) values ('admin', 'admin@admin.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'ADMIN');

--user
insert into users (name, email, password, role) values ('user', 'user@user.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'USER');

 --customer
 insert into customer (name, email) values ('customer', 'customer@customer.com');

 --category
 insert into category (name, description)
 values
 ('computer', 'computer items'),
 ('groceries', 'household / food items');

 --product
 insert into product (name, description, cost_price, price, available_quantity, min_quantity, max_quantity, category_id)
 values
 ('Mac pro', 'Mac pro 2019', 10000, 12000, 9, 5, 20,1),
 ('HP 14 inches', 'New HP 2020', 8000, 10000, 2, 10, 20, 1);

