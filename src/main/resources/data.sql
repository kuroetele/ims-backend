-- users
insert into users (name, email, password, role, phone, address)
values
('admin', 'admin@admin.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'ADMIN', '00000000000', 'Lagos'),
('user', 'user@user.com', '$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi', 'SALES_PERSON', '00000000000', 'Lagos');

 --customer
 insert into customer (name, email, phone, address) values ('Mr. Henry George', 'customer@customer.com', '00000000000', 'Lagos');

 --category
 insert into category (name, description)
 values
 ('computer', 'computer items'),
 ('groceries', 'household / food items');

 --product
 insert into product (name, description, cost_price, price, available_quantity, min_quantity, max_quantity, category_id, serial_number)
 values
 ('Mac pro', 'Mac pro 2019', 10000, 12000, 9, 12, 20,1, '1597179176678'),
 ('HP 14 inches', 'New HP 2020', 8000, 10000, 2, 10, 20, 1, '1597179176670'),
 ('Samsung TV', 'Samsung TV', 8000, 10000, 5, 10, 20, 1, '1597179176671'),
 ('Dell Latitude', 'Dell Latitude', 8000, 10000, 5, 10, 20, 1, '1597179176672'),
 ('Chromecast', 'Chromecast', 8000, 10000, 5, 10, 20, 1, '1597179176673'),
 ('First stick', 'First stick', 8000, 10000, 5, 10, 20, 1, '1597179176674'),
 ('Roku box', 'Roku box', 8000, 10000, 5, 10, 20, 1, '1597179176675');

--setting
 insert into setting (company_name, address, vat_percentage, phone, email, currency, loyalty_points_percentage)
  values ('IMS', 'Lagos', 5, '00000000000', 'ims@gmail.com', '₦', 10);

--menu
insert into menu (name, icon, route, role, priority)
values
('Dashboard', 'fa fa-home', 'dashboard', 'SALES_PERSON', 1),
('Customer', 'fa fa-user-o', 'customer', 'SALES_PERSON', 3),
('Manage User', 'fa fa-users', 'user', 'ADMIN',2),
('Manage Product', 'fa fa-product-hunt', '', 'ADMIN',4),
('Manage Sales', 'fa fa-credit-card', '', 'SALES_PERSON',5),
('Report', 'fa fa-bug', 'dashboard', 'SALES_PERSON',6),
('Setting', 'fa fa-cog', 'setting', 'ADMIN',7);


--sub-menu
insert into sub_menu (name, route, menu_id, priority, role, is_visible)
values
('Category','category', 4, 1, 'ADMIN', true),
('Product','product', 4, 2, 'ADMIN', true),
('New Sales','sales', 5, 1, 'SALES_PERSON', true),
('Sale History','sales-history', 5, 2, 'SALES_PERSON', true),
('Sale Invoice Details','sales-invoice-details', 5, 2, 'SALES_PERSON', false),
('Sale Report','sales-report', 6, 2, 'SALES_PERSON', true),
('Top Selling Products','top-product-report', 6, 3, 'ADMIN', true),
('Profile-Settings','profile-settings', 7, 2, 'SALES_PERSON', false);
