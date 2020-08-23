package git.soulbgm.common.annotation;

import git.soulbgm.common.enums.TimeType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Calendar;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Time生成策略
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-08-11 15:02:41
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface TimeBuild {

    /**
     * 生成Time的类型
     *
     * @return
     */
    TimeType timeType() default TimeType.NOW;

    /**
     * 格式化日期格式
     *
     * @return
     */
    String format() default "yyyy-MM-dd HH:mm:ss";

    /**
     * 偏移的对象
     *
     * @return
     */
    int field() default Calendar.HOUR_OF_DAY;

    /**
     * 偏移的数值 正数往未来时间偏移 负数往过去时间偏移
     *
     * @return
     */
    int amount() default 1;
}
