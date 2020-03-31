INSERT into Customers(name, address)
VALUES ('Bjudkakor AB', 'Ystad'),
       ('Finkakor AB', 'Helsingborg'),
       ('Gästkakor AB', 'Hässleholm'),
       ('Kaffebröd AB', 'Landskrona'),
       ('Kalaskakor AB', 'Trelleborg'),
       ('Partykakor AB', 'Kristianstad'),
       ('Skånekakor AB', 'Perstorp');

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
       ('Ground roasted nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Icing sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Marzipan', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Potato starch', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Roasted chopped nuts', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Sodium bicarbonate', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Vanilla', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Vanilla sugar', 500000, 'g', UNIX_TIMESTAMP(), 500000),
       ('Wheat flour', 500000, 'g', UNIX_TIMESTAMP(), 500000);