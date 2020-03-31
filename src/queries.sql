CREATE TABLE Customers
(
    name    varchar(30),
    address varchar(100)
);

create table Pallets
(
    id             integer primary key auto_increment,
    prod_date      long,
    blocked        bool,
    location       varchar(50),
    delivered_date long
)