# phoenix-example

#### 介绍
spring boot + tkMapper + PageHelper + phoenix 的使用例子

#### 安装phoenix
1. 下载phoenix地址 http://archive.apache.org/dist/phoenix/  
根据hbase版本选择下载phoenix的版本如下:
![phoenix](https://images.gitee.com/uploads/images/2020/0825/150933_5ab3d07e_1861024.png "20200825145924.png")
2. 以apache-phoenix-4.14.1-HBase-1.2-bin.tar.gz文件为例子目录结构如下:
![phoenix](https://images.gitee.com/uploads/images/2020/0825/151613_b2485d51_1861024.png "2020-08-25_151503.png")
3. 将如下的文件拷贝到hbase安装目录的lib目录下  
**phoenix-4.14.1-HBase-1.2-client.jar**  
**phoenix-4.14.1-HBase-1.2-server.jar**  
**phoenix-core-4.14.1-HBase-1.2.jar**  
4. 重启hbase
5. 运行./bin/sqlline.py zk的IP:zk端口  
出现如下内容证明成功
```shell script
Building list of tables and columns for tab-completion (set fastconnect to true to skip)...
138/138 (100%) Done
Done
sqlline version 1.2.0
0: jdbc:phoenix:> 
```
6. 查看表
```shell script
0: jdbc:phoenix:> !table
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+---------+
| TABLE_CAT  | TABLE_SCHEM  | TABLE_NAME  |  TABLE_TYPE   | REMARKS  | TYPE_NAME  | SELF_REFERENCING_COL_NAME  | REF_GENERATION  | INDEX_STATE  | IMMUTAB |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+---------+
|            | SYSTEM       | CATALOG     | SYSTEM TABLE  |          |            |                            |                 |              | false   |
|            | SYSTEM       | FUNCTION    | SYSTEM TABLE  |          |            |                            |                 |              | false   |
|            | SYSTEM       | LOG         | SYSTEM TABLE  |          |            |                            |                 |              | true    |
|            | SYSTEM       | SEQUENCE    | SYSTEM TABLE  |          |            |                            |                 |              | false   |
|            | SYSTEM       | STATS       | SYSTEM TABLE  |          |            |                            |                 |              | false   |
+------------+--------------+-------------+---------------+----------+------------+----------------------------+-----------------+--------------+---------+
0: jdbc:phoenix:> !exit
Closing: org.apache.phoenix.jdbc.PhoenixConnection
```
7. 进入hbase查看是否存在这些表
```shell script
[root@node1 phoenix-4.14.1]# hbase shell
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/usr/local/hbase-1.2.4/lib/phoenix-4.14.1-HBase-1.2-client.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/local/hbase-1.2.4/lib/slf4j-log4j12-1.7.5.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/usr/local/hadoop-2.7.3/share/hadoop/common/lib/slf4j-log4j12-1.7.10.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
HBase Shell; enter 'help<RETURN>' for list of supported commands.
Type "exit<RETURN>" to leave the HBase Shell
Version 1.2.4, r67592f3d062743907f8c5ae00dbbe1ae4f69e5af, Tue Oct 25 18:10:20 CDT 2016

hbase(main):001:0> list
TABLE                                                                                                                                                      
SYSTEM.CATALOG                                                                                                                                             
SYSTEM.FUNCTION                                                                                                                                            
SYSTEM.LOG                                                                                                                                                 
SYSTEM.MUTEX                                                                                                                                               
SYSTEM.SEQUENCE                                                                                                                                            
SYSTEM.STATS                                                                                                                                               
6 row(s) in 0.3070 seconds

=> ["SYSTEM.CATALOG", "SYSTEM.FUNCTION", "SYSTEM.LOG", "SYSTEM.MUTEX", "SYSTEM.SEQUENCE", "SYSTEM.STATS"]
hbase(main):002:0> 
```
如果以上都成功显示则表示安装phoenix成功

# phoenix常用命令
1. 查看当前库中存在的表  
!tables
2. 查看表结构  
!describe 表名
3. 创建HBase映射表  
create table if not exists user (id bigint primary key, name varchar, address varchar, age integer, birthday date);
4. 添加列  
alter table user add username varchar,password varchar;
5. 删除列  
alter table user drop column username,password;
6. 插入或更新表数据  
upsert into 表名 (字段1,字段2,...) values (字段1值,字段2值,...);
7. 批量插入或更新表数据  
upsert into 表名 (字段1,字段2,...) select 字段1值,字段2值,... union all select 字段1值,字段2值,...;
8. 查询数据  
select * from 表名 where id = 1;
9. 分页查询数据  
select * from 表名 limit 10 offset 10;  
从第11行开始查10条
10. 删除表数据  
delete from 表名 where id = 1;
11. 删除表  
drop table user;
12. 退出phoenix命令行  
!quit 或 !exit

注: 
1. 字段后加上primary key，将自动与rowkey关联
2. 添加字段时不加双引号，字段名自动转为大写
3. 字段需要带上列族，如：”列族”.”列名” 类型  

# phoenix常用函数

*   [Aggregate Functions](#Aggregate_Functions)

	*   [AVG](#AVG)
	*   [COUNT](#COUNT)
	*   [APPROX_COUNT_DISTINCT](#APPROX_COUNT_DISTINCT)
	*   [MAX](#MAX)
	*   [MIN](#MIN)
	*   [SUM](#SUM)
	*   [PERCENTILE_CONT](#PERCENTILE_CONT)
	*   [PERCENTILE_DISC](#PERCENTILE_DISC)
	*   [PERCENT_RANK](#PERCENT_RANK)
	*   [FIRST_VALUE](#FIRST_VALUE)
	*   [LAST_VALUE](#LAST_VALUE)
	*   [FIRST_VALUES](#FIRST_VALUES)
	*   [LAST_VALUES](#LAST_VALUES)
	*   [NTH_VALUE](#NTH_VALUE)
	*   [STDDEV_POP](#STDDEV_POP)
	*   [STDDEV_SAMP](#STDDEV_SAMP)

*   [String Functions](#String_Functions)

	*   [SUBSTR](#SUBSTR)
	*   [INSTR](#INSTR)
	*   [TRIM](#TRIM)
	*   [LTRIM](#LTRIM)
	*   [RTRIM](#RTRIM)
	*   [LPAD](#LPAD)
	*   [LENGTH](#LENGTH)
	*   [REGEXP_SUBSTR](#REGEXP_SUBSTR)
	*   [REGEXP_REPLACE](#REGEXP_REPLACE)
	*   [REGEXP_SPLIT](#REGEXP_SPLIT)
	*   [UPPER](#UPPER)
	*   [LOWER](#LOWER)
	*   [REVERSE](#REVERSE)
	*   [TO_CHAR](#TO_CHAR)
	*   [COLLATION_KEY](#COLLATION_KEY)

*   [Time and Date Functions](#Time_and_Date_Functions)

	*   [TO_DATE](#TO_DATE)
	*   [TO_TIME](#TO_TIME)
	*   [TO_TIMESTAMP](#TO_TIMESTAMP)
	*   [CURRENT_TIME](#CURRENT_TIME)
	*   [CONVERT_TZ](#CONVERT_TZ)
	*   [TIMEZONE_OFFSET](#TIMEZONE_OFFSET)
	*   [NOW](#NOW)
	*   [YEAR](#YEAR)
	*   [MONTH](#MONTH)
	*   [WEEK](#WEEK)
	*   [DAYOFYEAR](#DAYOFYEAR)
	*   [DAYOFMONTH](#DAYOFMONTH)
	*   [DAYOFWEEK](#DAYOFWEEK)
	*   [HOUR](#HOUR)
	*   [MINUTE](#MINUTE)
	*   [SECOND](#SECOND)

*   [Numeric Functions](#Numeric_Functions)

	*   [ROUND](#ROUND)
	*   [CEIL](#CEIL)
	*   [FLOOR](#FLOOR)
	*   [TRUNC](#TRUNC)
	*   [TO_NUMBER](#TO_NUMBER)
	*   [RAND](#RAND)

*   [Array Functions](#Array_Functions)

	*   [ARRAY_ELEM](#ARRAY_ELEM)
	*   [ARRAY_LENGTH](#ARRAY_LENGTH)
	*   [ARRAY_APPEND](#ARRAY_APPEND)
	*   [ARRAY_PREPEND](#ARRAY_PREPEND)
	*   [ARRAY_CAT](#ARRAY_CAT)
	*   [ARRAY_FILL](#ARRAY_FILL)
	*   [ARRAY_TO_STRING](#ARRAY_TO_STRING)
	*   [ANY](#ANY)
	*   [ALL](#ALL)

*   [Math Functions](#Math_Functions)

	*   [SIGN](#SIGN)
	*   [ABS](#ABS)
	*   [SQRT](#SQRT)
	*   [CBRT](#CBRT)
	*   [EXP](#EXP)
	*   [POWER](#POWER)
	*   [LN](#LN)
	*   [LOG](#LOG)

*   [Other Functions](#Other_Functions)

	*   [MD5](#MD5)
	*   [INVERT](#INVERT)
	*   [ENCODE](#ENCODE)
	*   [DECODE](#DECODE)
	*   [COALESCE](#COALESCE)
	*   [GET_BIT](#GET_BIT)
	*   [GET_BYTE](#GET_BYTE)
	*   [OCTET_LENGTH](#OCTET_LENGTH)
	*   [SET_BIT](#SET_BIT)
	*   [SET_BYTE](#SET_BYTE)

# Aggregate Functions

## AVG

> `AVG(X)`
> 
> 平均值。如果没有选择任何行，则结果为NULL。只允许在select语句中使用聚合。返回值的数据类型与参数相同。

## COUNT

> `COUNT(*)`
> 
> 所有行或非空值的计数。此方法返回一个long。当使用DISTINCT时，它只计算DISTINCT值。如果没有选择任何行，结果为0。只允许在select语句中使用聚合。

## APPROX_COUNT_DISTINCT

> `APPROX_COUNT_DISTINCT(*)`
> 
> 所有行或非空值的近似不同计数。默认情况下，近似的相对误差小于0.00405。如果没有选择任何行，结果为0。只允许在select语句中使用聚合。

## MAX

> `MAX(NAME)`
> 
> 最大的值。如果没有选择任何行，则结果为NULL。只允许在select语句中使用聚合。返回值的数据类型与参数相同。

## MIN

> `MIN(NAME)`
> 
> 最小的值。如果没有选择任何行，则结果为NULL。只允许在select语句中使用聚合。返回值的数据类型与参数相同。

## SUM

> `SUM(NAME)`
> 
> 所有值的总和。如果没有选择任何行，则结果为NULL。只允许在select语句中使用聚合。返回值的数据类型与参数相同。

## PERCENTILE_CONT

> `PERCENTILE_CONT( 0.9 ) WITHIN GROUP (ORDER BY X ASC)`
> 
> 列中值的第n个百分位数。百分比值可以在0到1之间(含1)。只允许在select语句中使用聚合。返回值为十进制数据类型。

## PERCENTILE_DISC

> `PERCENTILE_DISC( 0.9 ) WITHIN GROUP (ORDER BY X DESC)`
> 
> 一个逆分布函数，假设一个离散分布模型。它接受一个百分比值和一个排序规范，并从集合中返回一个元素。null值在计算中被忽略。

## PERCENT_RANK

> `PERCENT_RANK( 100 ) WITHIN GROUP (ORDER BY X ASC)`
> 
> 假设值的百分比等级(如果插入到列中)。只允许在select语句中使用聚合。返回值为十进制数据类型。

## FIRST_VALUE

> `FIRST_VALUE( name ) WITHIN GROUP (ORDER BY salary DESC)`
> 
> 每个不同组中的第一个值按照规范的顺序排序。

## LAST_VALUE

> `LAST_VALUE( name ) WITHIN GROUP (ORDER BY salary DESC)`
> 
> 每个不同组中的最后一个值，按照规范的顺序排序。

## FIRST_VALUES

> `FIRST_VALUES( name, 3 ) WITHIN GROUP (ORDER BY salary DESC)`
> 
> 返回一个数组，最多包含每个不同组中第一个值的给定数值大小，该数组按照规范的顺序排列。

## LAST_VALUES

> `LAST_VALUES( name, 3 ) WITHIN GROUP (ORDER BY salary DESC)`
> 
> 返回一个数组，最多包含每个不同组中最后一个值的给定数值大小，该数组按照规范的顺序排序。

## NTH_VALUE

> `NTH_VALUE( name, 2 ) WITHIN GROUP (ORDER BY salary DESC)`
> 
> 每个不同组中的第n个值按照规范的顺序排列。

## STDDEV_POP

> `STDDEV_POP( X )`
> 
> 所有值的总体标准差。只允许在select语句中使用聚合。返回值为十进制数据类型。

## STDDEV_SAMP

> `STDDEV_SAMP( X )`
> 
> 所有值的样本标准差。只允许在select语句中使用聚合。返回值为十进制数据类型。

# String Functions

## SUBSTR

> `SUBSTR('[Hello]', 2, 5)`
> 
> `SUBSTR('Hello World', -5)`
> 
> 返回从基于1的位置开始的字符串的子字符串。
> 
> 如果使用零，位置是基于零的。如果开始索引是负的，那么开始索引相对于字符串的结尾。长度是可选的，如果没有提供，则返回字符串的其余部分。

## INSTR

> `INSTR('Hello World', 'World')`
> 
> `INSTR('Simon says', 'mon')`
> 
> `INSTR('Peace on earth', 'war')`
> 
> 返回第二个参数在第一个参数中初始出现的基于1的位置。如果第二个参数不包含在第一个参数中，则返回0。

## TRIM

> `TRIM(' Hello ')`
> 
> 从输入字符串中删除前导和尾随空格。

## LTRIM

> `LTRIM(' Hello')`
> 
> 从输入字符串中删除前导空格。

## RTRIM

> `RTRIM('Hello ')`
> 
> 从输入字符串中移除尾随空格。

## LPAD

> `LPAD('John',30)`
> 
> 用特定的填充字符(默认为空格)填充字符串表达式直到长度参数。

## LENGTH

> `LENGTH('Hello')`
> 
> 返回字符串的字符长度。

## REGEXP_SUBSTR

> `REGEXP_SUBSTR('na1-appsrv35-sj35', '[^-]+') evaluates to 'na1'`
> 
> 通过应用正则表达式从基于1的位置的偏移量返回字符串的子字符串。就像SUBSTR一样，如果开始索引为负，那么它相对于字符串的结尾。如果未指定，起始索引默认为1。

## REGEXP_REPLACE

> `REGEXP_REPLACE('abc123ABC', '[0-9]+', '#') evaluates to 'abc#ABC'`
> 
> 通过应用正则表达式并将匹配项替换为替换字符串，返回一个字符串。如果未指定替换字符串，则默认为空字符串。

## REGEXP_SPLIT

> `REGEXP_SPLIT('ONE,TWO,THREE', ',') evaluates to ARRAY['ONE', 'TWO', 'THREE']`
> 
> `REGEXP_SPLIT('ONE!#TWO#,!THREE', '[,!#]+') evaluates to ARRAY['ONE', 'TWO', 'THREE']`
> 
> 使用正则表达式将字符串拆分为VARCHAR数组。如果在正则表达式中具有特殊含义的字符要用作模式字符串中的正则分隔符，则必须使用反斜杠对它们进行转义。

## UPPER

> `UPPER('Hello')`
> 
> `UPPER('Hello', 'tr_TR')`
> 
> 返回字符串参数的大写字符串。如果提供了localeString(在Phoenix 4.14中可用)，它将标识用于转换的规则的地区。如果没有提供localeString，则使用默认的语言环境。localalestring必须是Java 6实现返回的Java .util. local . tostring()的形式。“zh TW STROKE”或“en US”或“fr fr”。

## LOWER

> `LOWER('HELLO')`
> 
> `LOWER('HELLO', 'en_US')`
> 
> 返回字符串参数的小写字符串。如果提供了localeString(在Phoenix 4.14中可用)，它将标识用于转换的规则的地区。如果没有提供localeString，则使用默认的语言环境。localalestring必须是Java 6实现返回的Java .util. local . tostring()的形式。“zh TW STROKE”或“en US”或“fr fr”。

## REVERSE

> `REVERSE('Hello')`
> 
> 返回字符串参数的反向字符串。

## TO_CHAR

> `TO_CHAR(myDate, '2001-02-03 04:05:06')`
> 
> `TO_CHAR(myDecimal, '#,##0.###')`
> 
> 将日期，时间，时间戳或数字格式化为字符串。 默认日期格式为yyyy-MM-dd HH：mm：ss，默认数字格式为＃，## 0。###。 有关详细信息，请参见java.text.SimpleDateFormat（获取日期/时间值）和java.text.DecimalFormat（获取数字）。 此方法返回一个字符串。

## COLLATION_KEY

> `SELECT NAME FROM EMPLOYEE ORDER BY COLLATION_KEY(NAME, 'zh_TW')`
> 
> 计算可用于以自然语言感知的方式对字符串排序的排序键。localalestring必须是Java 6实现返回的Java .util. local . tostring()的形式。“zh TW STROKE”或“en US”或“fr fr”。第三、第四和第五个参数是可选的，它们分别决定是否使用特殊的大写排序器、排序器的强度值和排序器的分解值。(见text。排序器，以了解强度和分解)。

# Time and Date Functions

## TO_DATE

> `TO_DATE('Sat, 3 Feb 2001 03:05:06 GMT', 'EEE, d MMM yyyy HH:mm:ss z')`
> 
> `TO_DATE('1970-01-01', 'yyyy-MM-dd', 'GMT+1')`
> 
> `date`
> 
> 解析字符串并返回日期。 请注意，返回的日期在内部表示为自Java纪元以来的毫秒数。 最重要的格式字符是：y年，M月，d日，H小时，m分钟，s秒。 默认格式字符串是yyyy-MM-dd HH：mm：ss。 有关格式的详细信息，请参见java.text.SimpleDateFormat。 默认情况下，解析日期时，GMT将用作时区。 但是，也可以提供时区ID。 这是一个时区ID，例如“ GMT + 1”。 如果提供“ local”作为时区ID，则将使用本地时区进行解析。 还可以将配置设置phoenix.query.dateFormatTimeZone设置为时区ID，这将导致GMT的默认值被配置的时区ID覆盖。 请参阅数据类型参考指南，以了解Apache Phoenix当前如何定义DATE数据类型。 此外，Phoenix支持ANSI SQL日期文字，其作用类似于单参数TO_DATE函数。

## TO_TIME

> `TO_TIME('2005-10-01 14:03:22.559')`
> 
> `TO_TIME('1970-01-01', 'yyyy-MM-dd', 'GMT+1')`
> 
> `time '2005-10-01 14:03:22.559'`
> 
> 将给定字符串转换为时间实例。如果没有提供日期格式，则默认为yyyy-MM-dd HH:mm:ss。SSS或配置属性phoenix.query.dateFormat定义的任何内容。配置设置phoenix.query。dateFormatTimeZone也可以设置为时区id，这将导致GMT的默认值被配置的时区id覆盖。此外，Phoenix支持ANSI SQL时间文字，其作用类似于时间函数的单参数。

## TO_TIMESTAMP

> `TO_TIMESTAMP('2005-10-01 14:03:22.559')`
> 
> `TO_TIMESTAMP('1970-01-01', 'yyyy-MM-dd', 'GMT+1')`
> 
> `timestamp '2005-10-01 14:03:22.559'`
> 
> 将给定字符串转换为时间戳实例。如果没有提供日期格式，则默认为yyyy-MM-dd HH:mm:ss。SSS或配置属性phoenix.query.dateFormat定义的任何内容。配置设置phoenix.query。dateFormatTimeZone也可以设置为时区id，这将导致GMT的默认值被配置的时区id覆盖。此外，Phoenix支持ANSI SQL时间戳文字，其作用类似于时间戳函数的单参数。

## CURRENT_TIME

> `CURRENT_TIME()`
> 
> 与CURRENT DATE()相同，只是返回类型为TIME的值。在这两种情况下，底层表示法都是作为长值的epoch时间。关于Apache Phoenix目前如何定义时间数据类型，请参阅数据类型参考指南。

## CONVERT_TZ

> `CONVERT_TZ(myDate, 'UTC', 'Europe/Prague')`
> 
> 将日期/时间从一个时区转换为另一个时区，并返回转换后的日期/时间值。

## TIMEZONE_OFFSET

> `TIMEZONE_OFFSET('Indian/Cocos', myDate)`
> 
> 返回特定日期/时间的时区偏移量（以分钟为单位），以分钟为单位。

## NOW

> `NOW()`
> 
> 返回当前日期，该日期在查询开始执行时绑定，基于拥有被查询表元数据的区域服务器上的当前时间。

## YEAR

> `YEAR(TO_DATE('2015-6-05'))`
> 
> 返回指定日期的年份。

## MONTH

> `MONTH(TO_TIMESTAMP('2015-6-05'))`
> 
> 返回指定日期的月份。

## WEEK

> `WEEK(TO_TIME('2010-6-15'))`
> 
> 返回指定日期的星期。

## DAYOFYEAR

> `DAYOFYEAR(TO_DATE('2004-01-18 10:00:10'))`
> 
> 返回指定日期的日期。

## DAYOFMONTH

> `DAYOFMONTH(TO_DATE('2004-01-18 10:00:10'))`
> 
> 返回指定日期所在月份的日期。

## DAYOFWEEK

> `DAYOFWEEK(TO_DATE('2004-01-18 10:00:10'))`
> 
> 返回指定日期的星期几。

## HOUR

> `HOUR(TO_TIMESTAMP('2015-6-05'))`
> 
> 返回指定日期的小时。

## MINUTE

> `MINUTE(TO_TIME('2015-6-05'))`
> 
> 返回指定日期的分钟。

## SECOND

> `SECOND(TO_DATE('2015-6-05'))`
> 
> 返回指定日期的第二个日期。

# Numeric Functions

## ROUND

> `ROUND(number)`
> 
> `ROUND(number, 2)`
> 
> `ROUND(timestamp)`
> 
> `ROUND(time, 'HOUR')`
> 
> `ROUND(date, 'MINUTE', 30)`
> 
> 将数字或时间戳表达式舍入为指定的最接近的小数位数或时间单位。
> 
> 如果表达式是数字类型，则第二个参数是用于舍入数字的小数位，默认为零。
> 
> 如果表达式是日期/时间类型，则第二个参数可以是列出的确定日期/时间的剩余精度的时间单位之一。 如果不存在默认值，则使用MILLISECONDS。
> 
> 乘法器仅适用于日期/时间类型，用于四舍五入为一个时间单位（即10分钟）的倍数，如果未指定，则默认为1。 此方法返回与其第一个参数相同的类型。

## CEIL

> `CEIL(number, 3)`
> 
> `CEIL(2.34)`
> 
> `CEIL(timestamp, 'SECOND', 30)`
> 
> `CEIL(date, 'DAY', 7)`
> 
> 和ROUND一样，除了四舍五入任何小数值到下一个偶数倍。

## FLOOR

> `FLOOR(timestamp)`
> 
> `FLOOR(date, 'DAY', 7)`
> 
> 与ROUND相同，只是四舍五入将任何分数值四舍五入到之前的偶数倍。

## TRUNC

> `TRUNC(timestamp, 'SECOND', 30)`
> 
> `TRUNC(date, 'DAY', 7)`
> 
> Same as FLOOR

## TO_NUMBER

> `TO_NUMBER('$123.33', '\u00A4###.##')`
> 
> 将字符串或日期/时间类型格式化为数字，可以选择接受格式字符串。
> 
> 有关格式的详细信息，请参见java.text.DecimalFormat。 对于日期，时间和timeStamp而言，结果是自纪元以来的时间（以毫秒为单位）。 此方法返回一个十进制数。

## RAND

> `RAND()`
> 
> `RAND(5)`
> 
> 产生介于0.0（含）和1.0（不含）之间的随机且均匀分布的double值的函数。 如果提供了种子，则在同一行的每次调用中返回的值都是相同的。 如果未提供种子，则每次调用的返回值都不同。 种子必须是常数。

# Array Functions

## ARRAY_ELEM

> `ARRAY_ELEM(my_array_col, 5)`
> 
> `ARRAY_ELEM(ARRAY[1,2,3], 1)`
> 
> 使用数组下标符号访问数组元素的替代方法。返回数组中指定位置的元素。位置是基于一的。

## ARRAY_LENGTH

> `ARRAY_LENGTH(my_array_col)`
> 
> `ARRAY_LENGTH(ARRAY[1,2,3])`
> 
> 返回数组的当前长度。

## ARRAY_APPEND

> `ARRAY_APPEND(my_array_col, my_element_col)`
> 
> `ARRAY_APPEND(ARRAY[1,2,3], 4) evaluates to ARRAY[1,2,3,4]`
> 
> 将给定元素追加到数组的末尾。

## ARRAY_PREPEND

> `ARRAY_PREPEND(my_element_col, my_array_col)`
> 
> ARRAY_PREPEND(0, ARRAY[1,2,3]) evaluates to ARRAY[0,1,2,3]
> 
> 将给定元素追加到数组的开头。

## ARRAY_CAT

> `ARRAY_CAT(my_array_col1, my_array_col2)`
> 
> `ARRAY_CAT(ARRAY[1,2], ARRAY[3,4]) evaluates to ARRAY[1,2,3,4]`
> 
> 连接输入数组并返回结果。

## ARRAY_FILL

> `ARRAY_FILL(my_element_col, my_length_col)`
> 
> `ARRAY_FILL(1, 3) evaluates to ARRAY[1,1,1]`
> 
> 返回一个用所提供的值和长度初始化的数组。

## ARRAY_TO_STRING

> `ARRAY_TO_STRING(my_array_col, my_delimiter_col, my_null_string_col)`
> 
> `ARRAY_TO_STRING(ARRAY['a','b','c'], ',') evaluates to 'a,b,c'`
> 
> `ARRAY_TO_STRING(ARRAY['a','b',null,'c'], ',') evaluates to 'a,b,c'`
> 
> `ARRAY_TO_STRING(ARRAY['a','b',null,'c'], ',', 'NULL') evaluates to 'a,b,NULL,c'`
> 
> 使用提供的分隔符和可选的空字符串连接数组元素，并返回结果字符串。如果nullString参数被省略或NULL，则数组中的任何空元素都将被跳过，不会在输出字符串中表示。

## ANY

> `1 = ANY(my_array)`
> 
> `10 &gt; ANY(my_array)`
> 
> 用于比较表达式的右侧，用于测试任意数组元素是否满足左侧的比较表达式。

## ALL

> `1 = ALL(my_array)`
> 
> `10 &gt; ALL(my_array)`
> 
> 用于比较表达式的右侧，以测试所有数组元素是否满足左侧的比较表达式。的数组。

# Math Functions

## SIGN

> `SIGN(number)`
> 
> `SIGN(1.1)`
> 
> `SIGN(-1)`
> 
> 以整数形式返回给定数值表达式的sgn函数。如果给定的数值表达式为负数，则返回值为-1;如果给定的数值表达式为0;如果给定的数值表达式为正，则为1。

## ABS

> `ABS(number)`
> 
> `ABS(1.1)`
> 
> `ABS(-1)`
> 
> 返回保持相同类型的给定数值表达式的绝对值。

## SQRT

> `SQRT(number)`
> 
> `SQRT(1.1)`
> 
> 以双精度返回给定非负数值表达式的正确的四舍五入平方根。

## CBRT

> `CBRT(number)`
> 
> `CBRT(1.1)`
> 
> `CBRT(-1)`
> 
> 以双精度返回给定数值表达式的立方根。

## EXP

> `EXP(number)`
> 
> `EXP(1.1)`
> 
> `EXP(-1)`
> 
> 返回以给定数值的幂作为双精度的欧拉数e。

## POWER

> `POWER(number, number)`
> 
> `POWER(3, 2)`````POWER(2, 3)````
> 
> 返回第一个参数的值，它的幂是第二个参数的双精度值。

## LN

> `LN(number)`
> 
> 以双精度返回给定正表达式的自然对数(以e为底)。

## LOG

> `LOG(3, 2)`
> 
> `LOG(2, 3)`
> 
> `LOG(2)`
> 
> 返回在第二个参数的基础上计算的第一个参数的对数。如果省略，则第二个参数将使用以10为基数的参数。

# Other Functions

## MD5

> `MD5(my_column)`
> 
> 计算参数的MD5散列，以二进制(16)的形式返回结果。

## INVERT

> `INVERT(my_column)`
> 
> 翻转。返回类型将与参数相同。

## ENCODE

> `ENCODE(myNumber, 'BASE62')`
> 
> 根据提供的编码格式对表达式进行编码，并返回结果字符串。对于BASE62，将给定的以10为基数的数字转换为以62为基数的数字，并返回一个表示该数字的字符串。

## DECODE

> `DECODE('000000008512af277ffffff8', 'HEX')`
> 
> 根据提供的编码格式对表达式进行解码，并将结果值作为VARBINARY返回。对于“十六进制”，将十六进制字符串表达式转换为二进制表示，提供通过控制台输入二进制数据的机制。

## COALESCE

> `COALESCE(last_update_date, CURRENT_DATE())`
> 
> 如果不为null，返回第一个参数的值，否则返回第二个参数的值。用于保证UPSERT SELECT命令中的列计算值为非空值。

## GET_BIT

> `GET_BIT(CAST('FFFF' as BINARY), 1)`
> 
> 检索给定二进制值中给定索引处的位。

## GET_BYTE

> `GET_BYTE(CAST('FFFF' as BINARY), 1)`
> 
> 在给定的二进制值中检索给定索引处的字节。

## OCTET_LENGTH

> `OCTET_LENGTH(NAME)`
> 
> 返回二进制值中的字节数。

## SET_BIT

> `SET_BIT(CAST('FFFF' as BINARY), 1, 61)`
> 
> 用提供的新值替换二进制值中给定索引处的位。

## SET_BYTE

> `SET_BYTE(CAST('FFFF' as BINARY), 1, 61)`
> 
> 用提供的新值替换二进制值中给定索引处的字节。
