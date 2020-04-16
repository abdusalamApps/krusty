INSERT into Customers(name, address)
VALUES ('Bjudkakor AB', 'Ystad'),
       ('Finkakor AB', 'Helsingborg'),
       ('Gästkakor AB', 'Hässleholm'),
       ('Kaffebröd AB', 'Landskrona'),
       ('Kalaskakor AB', 'Trelleborg'),
       ('Partykakor AB', 'Kristianstad'),
       ('Skånekakor AB', 'Perstorp'),
       ('Småbröd AB', 'Malmö');

INSERT into Cookies(name)
VALUES ('Amneris'),
       ('Berliner'),
       ('Nut cookie'),
       ('Nut ring'),
       ('Tango'),
       ('Almond delight');

INSERT into RawMaterials(name, amount, unit, last_bought_date, last_bought_amount)
VALUES ('Bread crumbs', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Butter', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Chocolate', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Chopped almonds', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Cinnamon', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Egg whites', 500000, 'ml', UNIX_TIMESTAMP(), 500000),
       ('Eggs', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Fine-ground nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Flour', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Ground, roasted nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Icing sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Marzipan', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Potato starch', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Roasted, chopped nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Sodium bicarbonate', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Vanilla', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Vanilla sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Wheat flour', 500000, 'g', UNIX_TIMESTAMP(), 500000);


insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Almond delight', 'Butter', 400, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Almond delight', 'Chopped almonds', 279, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Almond delight', 'Cinnamon', 10, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Almond delight', 'Flour', 400, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Almond delight', 'Sugar', 270, 'g');

insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Amneris', 'Butter', 250, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Amneris', 'Eggs', 250, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Amneris', 'Marzipan', 750, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Amneris', 'Potato starch', 25, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Amneris', 'Wheat flour', 25, 'g');

insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Butter', 250, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Chocolate', 50, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Eggs', 50, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Flour', 350, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Icing sugar', 100, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Berliner', 'Vanilla sugar', 5, 'g');

insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Bread crumbs', 125, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Chocolate', 50, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Egg whites', 350, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Fine-ground nuts', 750, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Ground, roasted nuts ', 625, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut cookie', 'Sugar', 375, 'g');

insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut ring', 'Butter', 450, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut ring', 'Flour', 450, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut ring', 'Icing sugar', 190, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Nut ring', 'Roasted, chopped nuts', 225, 'g');

insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Tango', 'Butter', 200, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Tango', 'Flour', 300, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Tango', 'Sodium bicarbonate', 4, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Tango', 'Sugar', 250, 'g');
insert into Recipes (cookie_name, raw_material, amount, unit)
values ('Tango', 'Vanilla', 2, 'g');
