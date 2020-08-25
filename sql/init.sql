DROP TABLE USER;
CREATE TABLE IF NOT EXISTS XF.USER (ID BIGINT PRIMARY KEY, NAME VARCHAR, ADDRESS VARCHAR, AGE INTEGER, BIRTHDAY DATE);

upsert into user (id,name,address,age,birthday) values (1,'李四','beijing',18,'2020-08-24 18:05:10');
upsert into user (id,name,address,age,birthday) select 1,'李1','beijing',18,to_date('2020-08-24 18:05:10','yyyy-MM-dd HH:mm:ss') union all select 2,'李2','beijing',18,to_date('2020-08-24 18:05:10','yyyy-MM-dd HH:mm:ss') union all select 3,'李3','beijing',18,to_date('2020-08-24 18:05:10','yyyy-MM-dd HH:mm:ss');
