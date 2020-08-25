package git.soulbgm.utils;

import git.soulbgm.common.annotation.IdBuild;
import git.soulbgm.common.annotation.TimeBuild;
import git.soulbgm.common.enums.IdType;
import git.soulbgm.common.enums.TimeType;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;

/**
 * 实体类填充字段
 *
 * @author SoulBGM
 * @date 2020-08-12
 */
public class ModelBuilder {

    public static <T> void builder(T t) {
        idBuilder(t);
        timeBuilder(t);
    }

    /**
     * id 生成器
     * 主要判断条件是 看model中是否有Id的注解
     * 如果有接着检查是否有IdBuild的注解
     * 然后获取IdBuild中IdType的值
     * 如果IdType = UUID 则生成UUID
     * 如果IdType = SEQUENCE 则通过SEQUENCE生成
     *
     * @param t
     */
    public static <T> void idBuilder(T t) {
        // ① 获得实例的Class
        Class<?> tClass = t.getClass();
        // ② 获得Class的所有Field
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            // ③ 循环判断所有的Field是否有@IdBuild注解
            if (field.isAnnotationPresent(IdBuild.class)) {
                // ④ 有@IdBuild注解的Field
                IdBuild idBuild = field.getAnnotation(IdBuild.class);
                // ⑤ 判断是否有值 如果有值则跳出循环
                Object sourceVal = ClassUtil.executeGetMethod(t, field);
                if (sourceVal != null) {
                    break;
                }
                // ⑥ 判断IdBuild.value()的值是属于什么类型
                Object val = null;
                if (idBuild.value() == IdType.UUID) {
                    // 如果idType类型等于IdType.UUID
                    val = SequenceBuilder.uuid();
                } else if (idBuild.value() == IdType.SEQUENCE) {
                    // 如果idType类型等于IdType.SEQUENCE
                    val = SequenceBuilder.sequenceId();
                    if (field.getType() == String.class) {
                        val = String.valueOf(val);
                    }
                }
                // ⑦ 通过不同类型使用反射的invke()方法生成ID
                ClassUtil.executeSetMethod(t, field, val);
            }
        }
    }

    /**
     * time 生成器
     * 主要判断条件是 看model中是否有TimeBuild的注解
     * 如果有获取TimeBuild中TimeType的值
     * 如果IdType = NOW 则生成现在的时间
     *
     * @param t
     */
    public static <T> void timeBuilder(T t) {
        // ① 获得实例的Class
        Class<?> tClass = t.getClass();
        // ② 获得Class的所有Field
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            // ③ 循环判断所有的Field是否有@TimeBuild注解
            if (field.isAnnotationPresent(TimeBuild.class)) {
                // ④ 有@TimeBuild注解的Field
                TimeBuild timeBuild = field.getAnnotation(TimeBuild.class);
                // ⑤ 判断是否有值 如果有值则跳出循环
                Object sourceVal = ClassUtil.executeGetMethod(t, field);
                if (sourceVal != null) {
                    break;
                }
                Calendar calendar = Calendar.getInstance();
                Object val = null;
                // ⑥ 判断TimeBuild.timeType()的值是动态时间还是现在时间
                if (timeBuild.timeType() == TimeType.DYNAMIC_DATE) {
                    // ⑦ 如果是动态时间那么获取偏移的对象和偏移的数值进行偏移日期
                    DateUtil.offsetDate(calendar, timeBuild.field(), timeBuild.amount());
                }

                if (field.getType() == long.class || field.getType() == Long.class) {
                    val = calendar.getTimeInMillis();
                } else if (field.getType() == Date.class) {
                    val = calendar.getTime();
                } else if (field.getType() == String.class) {
                    val = DateUtil.format(calendar.getTime(), timeBuild.format());
                }
                // ⑧ 通过使用反射的invke()方法生成时间
                ClassUtil.executeSetMethod(t, field, val);
            }
        }
    }

}
