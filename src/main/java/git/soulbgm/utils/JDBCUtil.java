package git.soulbgm.utils;

import git.soulbgm.pojo.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 基于JDBC封装的增删改查工具类
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2019-04-01 14:57
 */
@Slf4j
public class JDBCUtil {

    private static final String DATABASE_TYPE_MYSQL = "mysql";
    private static final String DATABASE_TYPE_ORACLE = "oracle";
    private static final String DATABASE_TYPE_POSTGRESQL = "postgresql";
    private static final String DATABASE_TYPE_SQLSERVER = "sqlserver";
    private static final String DATABASE_TYPE_KDB = "inspur";
    private static final String DATABASE_TYPE_DB2 = "db2";
    private static final String DATABASE_TYPE_GBASE = "gbase";
    private static final String DATABASE_TYPE_KING_BASE_8 = "kingbase8";


    private static String driver = "org.apache.phoenix.jdbc.PhoenixDriver";
    private static String url = "jdbc:phoenix:node1:2181/hbase";
    private static String username;
    private static String password;
    private static String databaseType;

    public static void main(String[] args) {
        User user = new User();
        user.setId("2");
        user.setName("张三");
        user.setAddress("北京");
        user.setAge("26");
        user.setBirthday("2020-08-24");
        int result = JDBCUtil.executeUpdate("upsert into user (id,name,address,age,birthday) values (?,?,?,?,?)",
                new String[]{user.getId(), user.getName(), user.getAddress(), user.getAge(), user.getBirthday()});
        System.out.println(result);
        /*List<User> userList = JDBCUtil.find("select * from user", null, User.class);
        System.out.println(userList);*/

    }

    static {
        //1.加载驱动程序
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行插入操作
     *
     * @param t 插入实体类
     * @return 插入成功1 否则0
     */
    public static <T> int insert(T t) {
        if (t == null) {
            return 0;
        }
        String name = t.getClass().getSimpleName();
        Field[] fields = t.getClass().getDeclaredFields();
        String insertSQL = createInsertSQL(name, fields);
        Object[] param = new Object[fields.length];
        for (int i = 0; i < fields.length; i++) {
            param[i] = ClassUtil.executeGetMethod(t, fields[i]);
        }
        return executeUpdate(insertSQL, param);
    }

    /**
     * 执行修改操作
     *
     * @param t 修改实体类
     * @return 修改成功1 否则0
     */
    public static <T> int update(T t, String whereFieldName) {
        if (t == null) {
            return 0;
        }
        Class<?> tClass = t.getClass();
        String name = tClass.getSimpleName();
        Field[] fields = tClass.getDeclaredFields();
        String updateSQL = createUpdateSQL(t, name, fields, whereFieldName);
        List<Object> list = new ArrayList<>(fields.length);
        Field whereField = null;
        for (Field field : fields) {
            if (!field.getName().equals(whereFieldName)) {
                Object val = ClassUtil.executeGetMethod(t, field);
                if (val != null) {
                    list.add(val);
                }
            } else {
                whereField = field;
            }
        }
        list.add(ClassUtil.executeGetMethod(t, whereField));
        return executeUpdate(updateSQL, list.toArray());
    }

    /**
     * 查询数据
     *
     * @param sql    SQL语句
     * @param param  SQL语句携带的参数
     * @param classz 反射回的类型
     * @return 查询数据集合
     */
    public static <T> List<T> find(String sql, Object[] param, Class<T> classz) {
        Connection connection = getConnection();
        PreparedStatement ps = paramAssemble(connection, sql, param);
        if (ps == null) {
            return null;
        }
        List<T> list = new ArrayList<>();
        Field[] fields = classz.getDeclaredFields();
        ResultSet rs = null;
        try {
            rs = ps.executeQuery();
            while (rs.next()) {
                T obj = classz.newInstance();
                for (Field field : fields) {
                    loadData(rs, obj, field);
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, rs);
        }
        return list;
    }

    /**
     * 查询一个对象
     *
     * @param sql    SQL语句
     * @param param  SQL语句携带的参数
     * @param classz 反射回的类型
     * @return 查询数据
     */
    public static <T> T findOne(String sql, Object[] param, Class<T> classz) {
        Connection connection = getConnection();
        PreparedStatement ps = paramAssemble(connection, sql, param);
        if (ps == null) {
            return null;
        }
        Field[] fields = classz.getDeclaredFields();
        ResultSet rs = null;
        T obj = null;
        try {
            rs = ps.executeQuery();
            if (rs.next()) {
                obj = classz.newInstance();
                for (Field field : fields) {
                    loadData(rs, obj, field);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(connection, ps, rs);
        }
        return obj;
    }

    /**
     * 执行修改操作
     *
     * @param sql   SQL语句
     * @param param 参数
     * @return 受影响行数
     */
    public static int executeUpdate(String sql, Object[] param) {
        // 受影响的行数
        int affectedLine = 0;
        Connection connection = getConnection();
        PreparedStatement ps = paramAssemble(connection, sql, param);
        try {
            if (ps != null) {
                affectedLine = ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection, ps);
        }
        return affectedLine;
    }

    /**
     * 获取数据库连接
     *
     * @return 数据库连接
     */
    private static Connection getConnection() {
        //2.获取数据库连接
        try {
            Properties props = new Properties();
//            props.setProperty("user", username);
//            props.setProperty("password", password);
            if (DATABASE_TYPE_MYSQL.equalsIgnoreCase(databaseType)) {
                props.setProperty("useInformationSchema", "true");
            } else if (DATABASE_TYPE_ORACLE.equalsIgnoreCase(databaseType)) {
                props.setProperty("remarksReporting", "true");
            }
            return DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            log.error("获取数据库连接出现异常", e);
            return null;
        }
    }

    /**
     * 生成插入的SQL语句
     *
     * @param tableName 表名
     * @param fields    字段集合
     * @return insert语句
     */
    public static String createInsertSQL(String tableName, Field[] fields) {
        // 拼接插入语句
        StringBuilder sb = new StringBuilder("insert into ");
        sb.append(tableName);
        sb.append("(");
        for (Field field : fields) {
            sb.append(ClassUtil.camelToUnderline(field.getName()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append(") values (");
        for (int i = 0; i < fields.length; i++) {
            sb.append("?,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * 生成修改的SQL语句
     *
     * @param tableName      表名
     * @param fields         字段集合
     * @param whereFieldName 作为where条件的字段名称
     * @return insert语句
     */
    public static <T> String createUpdateSQL(T obj, String tableName, Field[] fields, String whereFieldName) {
        // 拼接插入语句
        StringBuilder sb = new StringBuilder("update ");
        sb.append(tableName);
        sb.append(" set ");
        for (Field field : fields) {
            if (!field.getName().equals(whereFieldName)) {
                Object val = ClassUtil.executeGetMethod(obj, field);
                if (val != null) {
                    sb.append(ClassUtil.camelToUnderline(field.getName()));
                    sb.append(" = ?,");
                }
            }
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" where ");
        sb.append(ClassUtil.camelToUnderline(whereFieldName));
        sb.append(" = ?");
        return sb.toString();
    }

    /**
     * 执行语句
     *
     * @param sql   SQL语句
     * @param param SQL语句携带的参数
     * @return 结果集
     */
    public static PreparedStatement paramAssemble(Connection connection, String sql, Object[] param) {
        checkConn(connection);
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            paramAssemble(connection, param, ps);
            return ps;
        } catch (SQLException e) {
            log.error("获取数据结果集出现异常", e);
            return null;
        }
    }

    /**
     * 执行赋值操作
     *
     * @param param 参数
     * @param ps    执行语句
     * @throws SQLException
     */
    private static void paramAssemble(Connection connection, Object[] param, PreparedStatement ps) throws SQLException {
        if (param != null && ps != null) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
        }
    }

    /**
     * 装载数据
     *
     * @param rs    结果集
     * @param obj   实体对象
     * @param field 字段
     * @return 装载成功true 反之false
     * @throws SQLException
     */
    private static <T> boolean loadData(ResultSet rs, T obj, Field field) throws SQLException {
        Class<?> type = field.getType();
        Object val = null;
        String columnName = ClassUtil.camelToUnderline(field.getName());
        if (type == String.class) {
            val = rs.getString(columnName);
        } else if (type == int.class || type == Integer.class) {
            val = rs.getInt(columnName);
        } else if (type == double.class || type == Double.class) {
            val = rs.getDouble(columnName);
        } else if (type == float.class || type == Float.class) {
            val = rs.getFloat(columnName);
        } else if (type == short.class || type == Short.class) {
            val = rs.getShort(columnName);
        } else if (type == boolean.class || type == Boolean.class) {
            val = rs.getBoolean(columnName);
        } else if (type == byte.class || type == Byte.class) {
            val = rs.getByte(columnName);
        } else if (type == long.class || type == Long.class) {
            val = rs.getLong(columnName);
        } else if (type == Date.class) {
            Timestamp timestamp = rs.getTimestamp(columnName);
            if (timestamp != null) {
                val = new Date(timestamp.getTime());
            } else {
                val = rs.getDate(columnName);
            }
        }
        return ClassUtil.executeSetMethod(obj, field, val);
    }

    /**
     * 关闭连接
     *
     * @param connection        数据库连接
     * @param preparedStatement 语句执行
     */
    public static void close(Connection connection, PreparedStatement preparedStatement) {
        close(connection, preparedStatement, null);
    }

    /**
     * 关闭资源
     *
     * @param connection        数据库连接
     * @param preparedStatement 语句执行
     * @param resultSet         结果集
     */
    public static void close(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            if (connection != null) {
                connection.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查数据库连接对象是否为空
     *
     * @param conn 数据库连接对象
     */
    private static void checkConn(Connection conn) {
        if (null == conn) {
            throw new NullPointerException("Connection object is null!");
        }
    }
}