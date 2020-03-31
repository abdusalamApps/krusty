set foreign_key_checks = 0;

drop table if exists Customers;
drop table if exists Orders;
drop table if exists Cookies;
drop table if exists Pallets;
drop table if exists RawMaterials;
drop table if exists Recipes;

set foreign_key_checks = 1;

CREATE TABLE Customers
(
    customer_name varchar(30) primary key,
    address       varchar(100)
);

create table Orders
(
    customer_name varchar(30),
    order_id      integer primary key auto_increment,
    order_date    long not null,
    foreign key (customer_name) references Customers (customer_name)
);

create table Cookies
(
    cookie_name varchar(30) primary key
);

create table Pallets
(
    pallet_id      integer primary key auto_increment,
    prod_date      long        not null,
    blocked        bool        not null,
    location       varchar(50) not null,
    delivered_date long        not null,
    order_id       integer,
    cookie_name    varchar(30),
    foreign key (order_id) references Orders (order_id),
    foreign key (cookie_name) references Cookies (cookie_name)
);

create table RawMaterials
(
    raw_material_name  varchar(30) primary key not null,
    amount             int                     not null check ( amount >= 0 ),
    unit               varchar(10)             not null,
    last_bought_date   long                    not null,
    last_bought_amount long                    not null
);

create table Recipes
(
    raw_material_name varchar(30),
    cookie_name       varchar(30),
    amount            integer     not null check ( amount > 0 ),
    unit              varchar(30) not null,
    foreign key (raw_material_name) references RawMaterials (raw_material_name),
    foreign key (cookie_name) references Cookies (cookie_name),
    primary key (raw_material_name, cookie_name)
);

