

create table users (id bigint not null auto_increment, password varchar(15) not null, email varchar(50) not null, encrypted_pwd varchar(255) not null, name varchar(50) not null, user_id varchar(255) , primary key (id)) engine=InnoDB;
create table orders (id bigint not null auto_increment, created_at datetime(6), order_id varchar(120) not null, product_id varchar(255) not null, qty varchar(255) not null, total_price varchar(255) not null, unit_price varchar(255) not null, user_id varchar(120) not null, primary key (id)) engine=InnoDB;
create table catalog (id bigint not null auto_increment, created_at datetime DEFAULT CURRENT_TIMESTAMP, product_id varchar(120) not null, product_name varchar(255) not null, stock integer not null, unit_price integer not null, primary key (id)) engine=InnoDB;

insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-001', 'Berlin', 100, 1500);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-003', 'stockholm', 120, 2000);


