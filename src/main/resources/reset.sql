set foreign_key_checks = 0;
delete from Pallets;
delete from Customers;
insert into Customers (name, address) VALUES ('Bjudkakor AB', 'Ystad');
insert into Customers (name, address) VALUES ('Finkakor AB', 'Helsingborg');
insert into Customers (name, address) VALUES ('Gästkakor AB', 'Hässleholm');
insert into Customers (name, address) VALUES ('Kaffebröd AB', 'Landskrona');
insert into Customers (name, address) VALUES ('Kalaskakor AB', 'Trelleborg');
insert into Customers (name, address) VALUES ('Partykakor AB', 'Kristianstad');
insert into Customers (name, address) VALUES ('Skånekakor AB', 'Perstorp');
insert into Customers (name, address) VALUES ('Småbröd AB', 'Malmö');
delete from RawMaterials;
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Bread crumbs', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Butter', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Chocolate', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Chopped almonds', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Cinnamon', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Egg whites', 500000, 'ml', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Eggs', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Fine-ground nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Flour', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Ground, roasted nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Icing sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Marzipan', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Potato starch', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Roasted, chopped nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Sodium bicarbonate', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Vanilla', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Vanilla sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000);
INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount) VALUES ('Wheat flour', 500000, 'g', UNIX_TIMESTAMP(), 500000);
delete from Cookies;
INSERT into Cookies(name) VALUES ('Amneris');
INSERT into Cookies(name) VALUES ('Berliner');
INSERT into Cookies(name) VALUES ('Nut cookie');
INSERT into Cookies(name) VALUES ('Nut ring');
INSERT into Cookies(name) VALUES ('Tango');
INSERT into Cookies(name) VALUES ('Almond delight');
delete from Recipes;
insert into Recipes (cookie_name, raw_material , amount ) values ('Almond delight' ,'Butter' , 400 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Almond delight' ,'Chopped almonds' , 279 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Almond delight' ,'Cinnamon' , 10 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Almond delight' ,'Flour' , 400 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Almond delight' ,'Sugar' , 270 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Amneris' ,'Butter' , 250 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Amneris' ,'Eggs' , 250 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Amneris' ,'Marzipan' , 750 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Amneris' ,'Potato starch' , 25 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Amneris' ,'Wheat flour' , 25 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Butter' , 250 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Chocolate' , 50 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Eggs' , 50 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Flour' , 350 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Icing sugar' , 100 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Berliner' ,'Vanilla sugar' , 5 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Bread crumbs' , 125 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Chocolate' , 50 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Egg whites' , 350 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Fine-ground nuts' , 750 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Ground, roasted nuts ' , 625 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut cookie' ,'Sugar' , 375 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut ring' ,'Butter' , 450 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut ring' ,'Flour' , 450 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut ring' ,'Icing sugar' , 190 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Nut ring' ,'Roasted, chopped nuts' , 225 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Tango' ,'Butter' , 200 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Tango' ,'Flour' , 300 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Tango' ,'Sodium bicarbonate' , 4 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Tango' ,'Sugar' , 250 );
insert into Recipes (cookie_name, raw_material , amount ) values ('Tango' ,'Vanilla' , 2 );
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Amneris');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Amneris');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Amneris');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Berliner');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Nut ring');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Nut ring');
-- insert into Pallets (blocked, cookie_name) VALUES (false, 'Tango');
set foreign_key_checks = 1;

