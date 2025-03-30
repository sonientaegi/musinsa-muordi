insert into category(name, display_sequence) values
    ('상의', 1),
    ('아우터', 2),
    ('바지', 3),
    ('스니커즈', 4),
    ('가방', 5),
    ('모자', 6),
    ('양말', 7),
    ('액세서리', 8);

insert into brand(name) values
                                ('A'),
                                ('B'),
                                ('C'),
                                ('D'),
                                ('E'),
                                ('F'),
                                ('G'),
                                ('H'),
                                ('I');


insert into product(brand_id, price) values
    (1,11200),
    (1,5500),
    (1,4200),
    (1,9000),
    (1,2000),
    (1,1700),
    (1,1800),
    (1,2300),
    (2,10500),
    (2,5900),
    (2,3800),
    (2,9100),
    (2,2100),
    (2,2000),
    (2,2000),
    (2,2200),
    (3,10000),
    (3,6200),
    (3,3300),
    (3,9200),
    (3,2200),
    (3,1900),
    (3,2200),
    (3,2100),
    (4,10100),
    (4,5100),
    (4,3000),
    (4,9500),
    (4,2500),
    (4,1500),
    (4,2400),
    (4,2000),
    (5,10700),
    (5,5000),
    (5,3800),
    (5,9900),
    (5,2300),
    (5,1800),
    (5,2100),
    (5,2100),
    (6,11200),
    (6,7200),
    (6,4000),
    (6,9300),
    (6,2100),
    (6,1600),
    (6,2300),
    (6,1900),
    (7,10500),
    (7,5800),
    (7,3900),
    (7,9000),
    (7,2200),
    (7,1700),
    (7,2100),
    (7,2000),
    (8,10800),
    (8,6300),
    (8,3100),
    (8,9700),
    (8,2100),
    (8,1600),
    (8,2000),
    (8,2000),
    (9,11400),
    (9,6700),
    (9,3200),
    (9,9500),
    (9,2400),
    (9,1700),
    (9,1700),
    (9,2400);


insert into muordi_display(category_id, product_id) values
(1,1),
(2,2),
(3,3),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(1,9),
(2,10),
(3,11),
(4,12),
(5,13),
(6,14),
(7,15),
(8,16),
(1,17),
(2,18),
(3,19),
(4,20),
(5,21),
(6,22),
(7,23),
(8,24),
(1,25),
(2,26),
(3,27),
(4,28),
(5,29),
(6,30),
(7,31),
(8,32),
(1,33),
(2,34),
(3,35),
(4,36),
(5,37),
(6,38),
(7,39),
(8,40),
(1,41),
(2,42),
(3,43),
(4,44),
(5,45),
(6,46),
(7,47),
(8,48),
(1,49),
(2,50),
(3,51),
(4,52),
(5,53),
(6,54),
(7,55),
(8,56),
(1,57),
(2,58),
(3,59),
(4,60),
(5,61),
(6,62),
(7,63),
(8,64),
(1,65),
(2,66),
(3,67),
(4,68),
(5,69),
(6,70),
(7,71),
(8,72);

-- -- 문제 1
-- select distinct on(category_id) category_id as CATEGORY_ID, category.name as CATEGORY_NAME, brand.id as BRAND_ID, brand.name as BRAND_NAME, product.price as PRICE
-- from MUORDI_DISPLAY_V2
-- inner join PRODUCT on MUORDI_DISPLAY_V2.PRODUCT_ID = PRODUCT.ID
-- inner join CATEGORY on MUORDI_DISPLAY_V2.CATEGORY_ID = CATEGORY.ID
-- inner join BRAND on PRODUCT.BRAND_ID = BRAND.ID
-- order by category_id asc, price asc, brand_id desc;
--
-- -- 문제 2
-- select BRAND_ID, sum(PRICE), count(*) from (
-- select distinct on(category_id, product.brand_id) category_id as CATEGORY_ID, product.brand_id as BRAND_ID, product.price as PRICE
-- from MUORDI_DISPLAY_V2
--     inner join PRODUCT on MUORDI_DISPLAY_V2.PRODUCT_ID = PRODUCT.ID
-- order by category_id, product.brand_id asc, price asc
-- )
-- group by BRAND_ID
-- having count(*)=8
-- order by sum(PRICE)
-- limit 1;
--
-- select distinct on(category_id) category.name as CATEGORY_NAME, product.price as PRICE
-- from MUORDI_DISPLAY_V2
--      inner join PRODUCT on MUORDI_DISPLAY_V2.PRODUCT_ID = PRODUCT.ID
--      inner join CATEGORY on MUORDI_DISPLAY_V2.CATEGORY_ID = CATEGORY.ID
-- where BRAND_ID=4
-- order by PRICE asc;
--
-- -- 문제 3
-- select distinct on(product.brand_id) brand.name, product.price
-- from MUORDI_DISPLAY_V2
--     inner join PRODUCT on MUORDI_DISPLAY_V2.PRODUCT_ID = PRODUCT.ID
--     inner join BRAND on PRODUCT.BRAND_ID = BRAND.ID
-- where category_id=1
-- order by price asc
-- limit 1;
--
-- select distinct on(product.brand_id) brand.name, product.price
-- from MUORDI_DISPLAY_V2
--     inner join PRODUCT on MUORDI_DISPLAY_V2.PRODUCT_ID = PRODUCT.ID
--     inner join BRAND on PRODUCT.BRAND_ID = BRAND.ID
-- where category_id=1
-- order by price desc
-- limit 1;

























