package git.soulbgm.common.annotation;

import git.soulbgm.common.enums.IdType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * ID生成策略
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-08-11 15:02:41
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface IdBuild {

    /**
     * 生成Id的类型
     *
     * @return
     */
    IdType value() default IdType.UUID;

}
