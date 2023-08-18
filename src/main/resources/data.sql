-- insert into tag (id, prtId,name, visible, created_date)
--   values (10, 1, "tag1", true, now())

 INSERT INTO region
(id, order_number, name_uz,name_en,name_ru,visible, created_date)
SELECT 100,1,'test_uz','test_en','test_ru',true,now()
    WHERE
    NOT EXISTS (
        SELECT id FROM region WHERE id = 100
    );