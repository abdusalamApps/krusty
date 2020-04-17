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
