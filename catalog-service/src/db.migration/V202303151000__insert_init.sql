

create table catalog (id bigint not null auto_increment, created_at datetime DEFAULT CURRENT_TIMESTAMP, product_id varchar(120) not null, product_name varchar(255) not null, stock integer not null, unit_price integer not null, primary key (id)) engine=InnoDB;

insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-001', 'Berlin', 100, 1500);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-002', 'Tokyo', 110, 1000);
insert into catalog(product_id, product_name, stock, unit_price)
values('CATALOG-003', 'stockholm', 120, 2000);