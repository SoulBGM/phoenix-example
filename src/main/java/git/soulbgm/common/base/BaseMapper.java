package git.soulbgm.common.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用的Mapper
 * 泛型(实体类)<T>的类型必须符合要求
 * 实体类按照如下规则和数据库表进行转换,注解全部是JPA中的注解:
 * 1. 表名默认使用类名,驼峰转下划线(只对大写字母进行处理),如UserInfo默认对应的表名为user_info。
 * 2. 表名可以使用@Table(name = “tableName”)进行指定,对不符合第一条默认规则的可以通过这种方式指定表名.
 * 3. 字段默认和@Column一样,都会作为表字段,表字段默认为Java对象的Field名字驼峰转下划线形式.
 * 4. 可以使用@Column(name = “fieldName”)指定不符合第3条规则的字段名
 * 5. 使用@Transient注解可以忽略字段,添加该注解的字段不会作为表字段使用.
 * 6. 建议一定是有一个@Id注解作为主键的字段,可以有多个@Id注解的字段作为联合主键.
 * 7. 可以在实体类字段上加@OrderBy注解可以默认实现升序排序 @OrderBy("DESC")为降序排序
 *
 * 所有的dao继承此类将具有以下通用方法
 * ===============================查询===============================
 * ===BaseSelectMapper下的通用方法===
 * List<T> selectAll();					        // 查询全部数据
 * T selectByPrimaryKey(Object key);	        // 通过主键查询
 * T selectOne(T record);			            // 通过实体查询单个数据
 * List<T> select(T record);			        // 通过实体查询多个数据
 * int selectCount(T record);			        // 通过实体查询实体数量
 * boolean existsWithPrimaryKey(Object key);    // 通过主键查询此主键是否存在
 * ===SelectByIdsMapper下的通用方法===
 * List<T> selectByIds(String var1);            // 通过多个主键查询数据
 * ===============================添加===============================
 * ===BaseInsertMapper下的通用方法===
 * int insert(T record);				        // 全部添加
 * int insertSelective(T record);		        // 选择性(不为null)的添加
 * ===MySqlMapper下的通用方法===
 * int insertList(List<T> list);		        // 批量插入
 * int insertUseGeneratedKeys(T record);        // 如果主键为自增可使用此方法获取添加成功的主键
 * ===OracleMapper下的通用方法===
 * int insertList(List<T> list);		        // 批量插入
 * ===============================修改===============================
 * ===BaseUpdateMapper下的通用方法===
 * int updateByPrimaryKey(T record);	        // 按照实体进行修改
 * int updateByPrimaryKeySelective(T record);   // 按照实体进行有选择的修改
 * ===============================删除===============================
 * ===BaseDeleteMapper下的通用方法===
 * int delete(T record);				        // 按照实体进行删除
 * int deleteByPrimaryKey(Object o);	        // 按照主键进行删除
 * ===IdsMapper下的通用方法===
 * int deleteByIds(String var1);                // 按照主键批量删除
 *
 * @author ${author}
 * @version V1.0
 * @date ${date}
 */
public interface BaseMapper<T> extends Mapper<T>, IdsMapper<T>, MySqlMapper<T>, PhoenixMapper<T> {
}
