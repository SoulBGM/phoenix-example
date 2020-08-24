package git.soulbgm.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Class的工具类
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2019-07-03 18:16:36
 */
public class ClassUtil {

    private static final String GET = "get";
    private static final String IS = "is";
    private static final String SET = "set";
    private static final char UNDERLINE = '_';

    /**
     * 通过反射的方式执行getXxx()方法
     *
     * @param obj   实体对象
     * @param field 字段
     * @return 执行了getXxx()方法之后的值
     */
    public static Object executeGetMethod(Object obj, Field field) {
        if (obj == null) {
            return null;
        }
        try {
            return obj.getClass().getMethod(buildGetMethod(field.getName(), field.getType() == boolean.class)).invoke(obj);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    /**
     * 通过反射的方法执行setXxx(xx)方法
     *
     * @param obj   实体对象
     * @param field 字段
     * @param val   执行set方法的值
     * @return 成功返回true，失败false
     */
    public static boolean executeSetMethod(Object obj, Field field, Object val) {
        if (obj == null) {
            return false;
        }
        try {
            Method method = obj.getClass().getMethod(buildSetMethod(field.getName()), field.getType());
            method.invoke(obj, val);
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * 通过禁用访问安全检查来获得字段的值
     *
     * @param obj       bean对象
     * @param fieldName 字段名称
     * @return 返回字段的值
     */
    public static <T> T getFieldVal(Object obj, String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Field field = objClass.getDeclaredField(fieldName);
        // 禁用访问安全检查
        field.setAccessible(true);
        Object val = field.get(obj);
        return val != null ? (T) val : null;
    }

    /**
     * 通过禁用访问安全检查来获得字段的值
     *
     * @param obj       bean对象
     * @param fieldName 字段名称
     * @param val       设置字段的值
     */
    public static void setFieldVal(Object obj, String fieldName, Object val) throws NoSuchFieldException, IllegalAccessException {
        Class<?> objClass = obj.getClass();
        Field field = objClass.getDeclaredField(fieldName);
        setFieldVal(obj, field, val);
    }

    /**
     * 通过禁用访问安全检查来获得字段的值
     *
     * @param obj   bean对象
     * @param field 字段名称
     * @param val   设置字段的值
     */
    public static void setFieldVal(Object obj, Field field, Object val) throws IllegalAccessException {
        // 禁用访问安全检查
        field.setAccessible(true);
        field.set(obj, val);
    }

    /**
     * 获取字段${clazz}注解
     *
     * @param field 字段
     * @param clazz Class对象
     * @return
     */
    public static <T extends Annotation> T getFieldAnnotation(Field field, Class<T> clazz) {
        if (field != null && clazz != null) {
            return field.getAnnotation(clazz);
        } else {
            return null;
        }
    }

    /**
     * 判断字段中是否存在${clazz}注解
     *
     * @param field 字段
     * @param clazz Class对象
     * @return
     */
    public static <T extends Annotation> boolean existFieldAnnotation(Field field, Class<T> clazz) {
        if (field != null && clazz != null) {
            return field.isAnnotationPresent(clazz);
        } else {
            return false;
        }
    }

    /**
     * 获取字段${clazz}注解
     *
     * @param method 方法
     * @param clazz  Class对象
     * @return
     */
    public static <T extends Annotation> T getMethodAnnotation(Method method, Class<T> clazz) {
        if (method != null && clazz != null) {
            return method.getAnnotation(clazz);
        } else {
            return null;
        }
    }

    /**
     * 判断字段中是否存在${clazz}注解
     *
     * @param method 方法
     * @param clazz  Class对象
     * @return
     */
    public static <T extends Annotation> boolean existMethodAnnotation(Method method, Class<T> clazz) {
        if (method != null && clazz != null) {
            return method.isAnnotationPresent(clazz);
        } else {
            return false;
        }
    }

    /**
     * 通过给字段名称构建出相应的get方法名称
     *
     * @param fieldName 字段名称
     * @return 组合之后的名称
     */
    public static String buildGetMethod(String fieldName, boolean isBoolean) {
        StringBuilder sb = new StringBuilder();
        if (isBoolean) {
            sb.append(IS);
        } else {
            sb.append(GET);
        }
        sb.append(Character.toUpperCase(fieldName.charAt(0)));
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    /**
     * 通过给字段名称构建出相应的set方法名称
     *
     * @param fieldName 字段名称
     * @return 组合之后的名称
     */
    public static String buildSetMethod(String fieldName) {
        StringBuilder sb = new StringBuilder(SET);
        sb.append(Character.toUpperCase(fieldName.charAt(0)));
        sb.append(fieldName.substring(1));
        return sb.toString();
    }

    /**
     * 驼峰字符转下划线字符
     *
     * @param param 驼峰字符
     * @return 下划线字符
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线字符转驼峰字符
     *
     * @param param 下划线字符
     * @return 驼峰字符
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获得新的代理实例
     *
     * @param clazz   Class对象
     * @param handler 代理对象的调用处理程序
     * @return 返回代理类
     */
    public static <T> T newProxyInstance(Class<T> clazz, InvocationHandler handler) {
        if (clazz == null || handler == null || !clazz.isInterface()) {
            return null;
        }
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
    }

    /**
     * 获得新的代理实例
     *
     * @param obj     需要代理的对象
     * @param handler 代理对象的调用处理程序
     * @return 返回代理类
     */
    public static <T> T newProxyInstance(T obj, InvocationHandler handler) {
        if (obj == null || handler == null || obj.getClass().getInterfaces() == null || obj.getClass().getInterfaces().length == 0) {
            return null;
        }
        return (T) Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), handler);
    }

    /**
     * 获得直接父类
     *
     * @param clazz Class对象
     * @return 返回父类Class
     */
    public static <T> Class<? super T> getSuperclass(Class<T> clazz) {
        if (clazz != null) {
            return clazz.getSuperclass();
        }
        return null;
    }

    /**
     * 获得直接父类 包含泛型参数
     *
     * @param clazz Class对象
     * @return 返回父类Class
     */
    public static Type getGenericSuperclass(Class clazz) {
        if (clazz != null) {
            return clazz.getGenericSuperclass();
        }
        return null;
    }

    /**
     * 获得直接父类 包含泛型参数
     *
     * @param clazz Class对象
     * @return 返回父类Class
     */
    public static ParameterizedType getParameterizedType(Class clazz) {
        Type type = getGenericSuperclass(clazz);
        if (type != null && type instanceof ParameterizedType) {
            return (ParameterizedType) type;
        }
        return null;
    }

    /**
     * 获得实际类型参数 (泛型)
     *
     * @param clazz Class对象
     * @return 返回实际类型参数列表
     */
    public static Type[] getActualTypeArguments(Class clazz) {
        ParameterizedType type = getParameterizedType(clazz);
        if (type != null) {
            return type.getActualTypeArguments();
        }
        return null;
    }

    /**
     * 获得class的所有实现的接口
     *
     * @param clazz Class对象
     * @return 返回所有实现的接口
     */
    public static AnnotatedType[] getInterfaces(Class clazz) {
        return clazz.getAnnotatedInterfaces();
    }

}