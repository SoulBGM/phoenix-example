package git.soulbgm.utils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 序列生成器
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-08-11 15:02:41
 */
public class SequenceBuilder {

    private static SnowflakeIdWorker seq = new SnowflakeIdWorker((int) (Math.random() * 30) + 1, (int) (Math.random() * 30) + 1);

    private static final int MAX_INTEGER_NUMBER = 2147483647;

    /**
     * 获取UUID
     *
     * @param flag true不带-字符  false带-字符
     * @return 不带-的uuid字符串
     */
    public static String uuid(boolean flag) {
        String uuid = UUID.randomUUID().toString();
        if (flag) {
            uuid = uuid.replaceAll("-", "");
        }
        return uuid;
    }

    /**
     * 获取UUID
     *
     * @return 不带-的uuid字符串
     */
    public static String uuid() {
        return uuid(true);
    }

    /**
     * 基于Twitter的Snowflake算法实现分布式高效有序ID生产黑科技
     *
     * @return 通过Snowflake雪花算法生成的ID
     */
    public static long sequenceId() {
        return seq.nextId();
    }

    private static AtomicInteger autoIncrementNumber = new AtomicInteger(1);

    /**
     * 如果主键为int类型并且数据库不支持自动生成的补救方式（严重不推荐）
     *
     * @return int类型可以容下的值
     */
    public static int intSequenceId() {
        try {
            if (autoIncrementNumber.get() >= MAX_INTEGER_NUMBER) {
                autoIncrementNumber.set(1);
            }
            return autoIncrementNumber.get();
        } finally {
            autoIncrementNumber.getAndAdd(1);
        }
    }

}
