package git.soulbgm.common.enums;

/**
 * ID生成几种策略
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-08-11 15:02:41
 */
public enum IdType {

    /**
     * 标识Id由数据库自增生成
     */
    JDBC,

    /**
     * 标识Id由UUID生成
     */
    UUID,

    /**
     * 标识Id使用基于Twitter的Snowflake算法生成分布式高效有序ID
     */
    SEQUENCE,

    /**
     * int可以容下的值（严重不推荐）
     */
    INT_SEQUENCE

}
