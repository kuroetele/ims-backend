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

--setting
 insert into setting (company_name, address, vat_percentage, phone, email, currency)
  values ('IMS', 'Lagos', 5, '000000000', 'ims@gmail.com', '₦');

--menu
insert into menu (name, icon, route)
values
('Dashboard', 'fa fa-home', 'dashboard'),
('Customer', 'fa fa-user-o', 'customer'),
('Manage User', 'fa fa-users', 'user'),
('Product Manage', 'fa fa-product-hunt', ''),
('Manage Sales', 'fa fa-credit-card', ''),
('Report', 'fa fa-bug', 'dashboard'),
('Setting', 'fa fa-cog', 'setting');


--sub-menu
insert into sub_menu (name, route, menu_id, priority)
values
('Category','category', 4, 1),
('Product','product', 4, 2),
('New Sales','sales', 5, 1),
('Sale History','sales-history', 5, 2),
('Sale Report','sales-report', 6, 2);
