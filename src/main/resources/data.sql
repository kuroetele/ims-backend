-- admin
insert into users (name, email, password, role) values ('admin', 'admin@admin.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'ADMIN');

--user
insert into users (name, email, password, role) values ('user', 'user@user.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'USER');

 --customer
 insert into customer (name, email, phone, address) values ('customer', 'customer@customer.com', '00000000', 'Lagos');

 --category
 insert into category (name, description)
 values
 ('computer', 'computer items'),
 ('groceries', 'household / food items');

 --product
 insert into product (name, description, cost_price, price, available_quantity, min_quantity, max_quantity, category_id, serial_number)
 values
 ('Mac pro', 'Mac pro 2019', 10000, 12000, 9, 5, 20,1, '1597179176678'),
 ('HP 14 inches', 'New HP 2020', 8000, 10000, 2, 10, 20, 1, '1597179176670');

--setting
 insert into setting (company_name, address, vat_percentage, phone, email, currency)
  values ('IMS', 'Lagos', 5, '000000000', 'ims@gmail.com', '₦');

--menu
insert into menu (name, icon, route, role)
values
('Dashboard', 'fa fa-home', 'dashboard', 'USER'),
('Customer', 'fa fa-user-o', 'customer', 'USER'),
('Manage User', 'fa fa-users', 'user', 'ADMIN'),
('Product Manage', 'fa fa-product-hunt', '', 'ADMIN'),
('Manage Sales', 'fa fa-credit-card', '', 'USER'),
('Report', 'fa fa-bug', 'dashboard', 'USER'),
('Setting', 'fa fa-cog', 'setting', 'ADMIN');


--sub-menu
insert into sub_menu (name, route, menu_id, priority, role, is_visible)
values
('Category','category', 4, 1, 'ADMIN', true),
('Product','product', 4, 2, 'ADMIN', true),
('New Sales','sales', 5, 1, 'USER', true),
('Sale History','sales-history', 5, 2, 'USER', true),
('Sale Invoice Details','sales-invoice-details', 5, 2, 'USER', false),
('Sale Report','sales-report', 6, 2, 'USER', true),
('Profile-Settings','profile-settings', 7, 2, 'USER', false);
