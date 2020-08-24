CREATE TABLE IF NOT EXISTS USER (ID VARCHAR NOT NULL PRIMARY KEY, NAME VARCHAR, ADDRESS VARCHAR, AGE VARCHAR, BIRTHDAY VARCHAR);

upsert into user (id,name,address,age,birthday) values ('1','李四','beijing','18','2020-08-24');
upsert into user (id,name,address,age,birthday) select ('1','李1','beijing','18','2020-08-24') union all select ('2','李2','beijing','18','2020-08-24') union all select ('3','李3','beijing','18','2020-08-24');
