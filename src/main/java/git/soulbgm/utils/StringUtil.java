package git.soulbgm.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 *
 * @author SoulBGM
 * @date 2018-04-24
 */
public class StringUtil {

    /**
     * 判断1个或多个字符串是否不为空
     * 判断条件 null 和 长度不为0
     *
     * @param str 字符串数组
     * @return 全部不为空返回true 否则false
     */
    public static boolean isNotEmpty(String... str) {
        for (String s : str) {
            if (s == null) {
                return false;
            }
            if (s.length() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断1个或多个字符串是否为空
     * 判断条件 null 和 长度不为0
     *
     * @param str 字符串数组
     * @return 一个或多个为空返回true 否则false
     */
    public static boolean isEmpty(String... str) {
        for (String s : str) {
            if (s == null) {
                return true;
            }
            if (s.trim().length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断1个或多个字符串数组是否不为空
     * 判断条件 null 和 长度不为0
     *
     * @param objects 对象多维数组
     * @return 全部不为空true 否则false
     */
    public static boolean isNotEmptyArray(Object[]... objects) {
        for (Object[] obj : objects) {
            if (obj == null) {
                return false;
            }
            if (obj.length == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 倍数创建字符串
     *
     * @param multiple 倍数
     * @param str      原始字符
     * @return N倍数的原始字符
     */
    public static String multipleCreateString(int multiple, String str) {
        String[] strings = new String[multiple];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = str;
        }
        return StringUtil.join("", strings);
    }

    /**
     * 缩进 num*4个空格
     *
     * @param num 缩进的次数
     * @return 缩进之后的字符串
     */
    public static String indent(int num) {
        String indent = "    ";
        return multipleCreateString(num, indent);
    }

    /**
     * 缩进 4个空格
     *
     * @return 缩进之后的字符串
     */
    public static String indent() {
        return indent(1);
    }

    /**
     * 源字符串是否包含给定的字符串
     *
     * @param source  源字符串
     * @param include 检查包含的字符串
     * @return 包含true 否则false
     */
    public static boolean isInclude(String source, String include) {
        return source != null && include != null && source.contains(include);
    }

    /**
     * 返回文字模式
     * 转移正则表达式的$字符
     *
     * @param str 需要转换的字符串
     * @return 转换之后的字符串
     */
    public static String quoteReplacement(String str) {
        if (str != null) {
            return Matcher.quoteReplacement(str);
        }
        return null;
    }

    /**
     * 返回文字模式
     * 可以使用此方法是正则表达式进行转换为普通字符串
     *
     * @param str 需要转换的字符串
     * @return 转换之后的字符串
     */
    public static String quote(String str) {
        if (str != null) {
            return Pattern.quote(str);
        }
        return null;
    }

    /**
     * 字符串全部替换
     *
     * @param source      源字符串
     * @param target      要替换的字符串
     * @param replacement 替换字符串
     * @return 替换之后的字符串
     */
    public static String replaceAll(String source, String target, String replacement) {
        if (source != null) {
            source = source.replaceAll(quote(target), quoteReplacement(replacement));
        }
        return source;
    }

    /**
     * String数组中是否包含给定字符串
     *
     * @param source   是否包含的字符串
     * @param strArray String数组
     * @return 包含true 否则false
     */
    public static boolean exist(String source, String... strArray) {
        return source != null && strArray != null && Arrays.binarySearch(strArray, source) >= 0;
    }

    /**
     * 将一组字符组合拼接
     *
     * @param strs 需要连接的动态参数
     * @return 连接之后的字符串
     */
    public static String joinBlank(String... strs) {
        return join("", strs);
    }

    /**
     * 将一组字符才以指定的字符链接起来
     *
     * @param linkStr 字符串与字符串连接之间的字符
     * @param strs    需要连接的动态参数
     * @return 连接之后的字符串
     */
    public static String join(String linkStr, String... strs) {
        if (isNotEmpty(strs) && linkStr != null) {
            StringBuilder result = new StringBuilder();
            for (String temp : strs) {
                result.append(temp);
                result.append(linkStr);
            }
            if (result.length() > linkStr.length()) {
                result.delete(result.length() - linkStr.length(), result.length());
            }
            return result.toString();
        }
        return null;
    }

    /**
     * 给字符规定额定长度 如果不足额定长度向⬅左填充空格
     *
     * @param str       原始字符串
     * @param strLength 额定长度
     * @return 额定长度的字符串
     */
    public static String leftFillSpace(String str, int strLength) {
        return ratedLength(str, strLength, " ", 0);
    }

    /**
     * 给字符规定额定长度 如果不足额定长度向➡右填充空格
     *
     * @param str       原始字符串
     * @param strLength 额定长度
     * @return 额定长度的字符串
     */
    public static String rightFillSpace(String str, int strLength) {
        return ratedLength(str, strLength, " ", 1);
    }

    /**
     * 给字符规定额定长度
     *
     * @param str       原始字符串
     * @param strLength 额定长度
     * @param fillStr   如果不足额定长度补位的字符 长度最好为1 否则会出现超出额定长度的问题
     * @param direction 补位的方向 0⬅左 其它为➡右
     * @return 额定长度的字符串
     */
    public static String ratedLength(String str, int strLength, String fillStr, int direction) {
        int strLen = str.length();
        StringBuilder sb = null;
        while (strLen < strLength) {
            sb = new StringBuilder();
            if (direction == 0) {
                // ⬅左补位
                sb.append(fillStr).append(str);
            } else {
                // ➡右补位
                sb.append(str).append(fillStr);
            }
            str = sb.toString();
            strLen = str.length();
        }
        return str;
    }

    /**
     * Object 转 String
     *
     * @param obj object对象
     * @return String对象
     */
    public static String objToStr(Object obj) {
        String str = String.valueOf(obj);
        return "null".equals(str) ? null : str;
    }

    /**
     * Object 转 String
     * 如果obj为null 则返回 defaultStr 否则 返回转成功后的obj字符串
     *
     * @param obj object对象
     * @return String对象
     */
    public static String objToStr(Object obj, String defaultStr) {
        String str = objToStr(obj);
        return str == null ? defaultStr : str;
    }

    /**
     * 删除起始字符
     *
     * @param source 源字符串
     * @param trim   需要删除的开头字符串
     * @return 修剪好的字符串
     */
    public static String trimStart(String source, String trim) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("^(" + trim + ")+", "");
    }

    /**
     * 删除末尾字符
     *
     * @param source 源字符串
     * @param trim   需要删除的末尾字符串
     * @return 修剪好的字符串
     */
    public static String trimEnd(String source, String trim) {
        if (source == null) {
            return null;
        }
        return source.replaceAll("(" + trim + ")+$", "");
    }

    /**
     * 删除起始和末尾字符
     *
     * @param str  源字符串
     * @param trim 需要删除的开头和末尾字符串
     * @return 修剪好的字符串
     */
    public static String trimStartEnd(String str, String trim) {
        str = trimStart(str, trim);
        str = trimEnd(str, trim);
        return str;
    }

    /**
     * 随机生成一定长度的字符串
     *
     * @param length 生成字符串的长度
     * @return 生成的字符串
     */
    public static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    /**
     * 随机生成长度为30的字符串
     *
     * @return 30位的字符串
     */
    public static String getRandomString() {
        return getRandomString(30);
    }

    /**
     * 根据给定的字符截取给定字符后面的字符串
     * 从后往前搜索给定字符
     * 例子：cutOutEndString("123.txt", ".", false);   结果：txt
     * 例子：cutOutEndString("123.txt", ".", true);   结果：.txt
     *
     * @param source       源字符串
     * @param cutOutSymbol 寻找的字符串
     * @param last         true从末尾处开始查找给定字符串    false从开始处开始查找给定字符串
     * @param flag         true包含给定字符串   false不包含
     * @return 修剪好的字符串
     */
    public static String cutOutEndString(String source, String cutOutSymbol, boolean last, boolean flag) {
        if (isEmpty(source, cutOutSymbol)) {
            return null;
        }
        int increment = cutOutSymbol.length();
        if (flag) {
            increment = 0;
        }
        String str = null;
        int index = 0;
        if (last) {
            index = source.lastIndexOf(cutOutSymbol);
        } else {
            index = source.indexOf(cutOutSymbol);
        }
        if (index != -1) {
            str = source.substring(index + increment, source.length());
        }
        return str;
    }

    /**
     * 根据给定的字符截取给定字符后面的字符串
     * 从后往前搜索给定字符
     * 例子：cutOutEndString("123.txt", ".");   结果：txt
     *
     * @param source       源字符串
     * @param cutOutSymbol 寻找的字符串
     * @param last         true从末尾处开始查找给定字符串    false从开始处开始查找给定字符串
     * @return 修剪好的字符串
     */
    public static String cutOutEndString(String source, String cutOutSymbol, boolean last) {
        return cutOutEndString(source, cutOutSymbol, last, false);
    }

    /**
     * 根据给定的字符截取给定字符前面的字符串
     * 从后往前搜索给定字符
     * 例子：cutOutEndString("123.txt", ".", false);   结果：123
     * 例子：cutOutEndString("123.txt", ".", true);   结果：123.
     *
     * @param source       源字符串
     * @param cutOutSymbol 寻找的字符串
     * @param last         true从末尾处开始查找给定字符串    false从开始处开始查找给定字符串
     * @param flag         true包含给定字符串 false不包含
     * @return 修剪好的字符串
     */
    public static String cutOutTopString(String source, String cutOutSymbol, boolean last, boolean flag) {
        if (isEmpty(source, cutOutSymbol)) {
            return null;
        }
        int increment = 0;
        if (flag) {
            increment = cutOutSymbol.length();
        }
        String str = null;
        int index = 0;
        if (last) {
            index = source.lastIndexOf(cutOutSymbol);
        } else {
            index = source.indexOf(cutOutSymbol);
        }
        if (index != -1) {
            str = source.substring(0, index + increment);
        }
        return str;
    }

    /**
     * 根据给定的字符截取给定字符前面的字符串
     * 从后往前搜索给定字符
     * 例子：cutOutEndString("123.txt", ".");   结果：123
     *
     * @param source       源字符串
     * @param cutOutSymbol 寻找的字符串
     * @param last         true从末尾处开始查找给定字符串    false从开始处开始查找给定字符串
     * @return 修剪好的字符串
     */
    public static String cutOutTopString(String source, String cutOutSymbol, boolean last) {
        return cutOutTopString(source, cutOutSymbol, last, false);
    }

    /**
     * 转换${start}开始${end}结束(不包括${end}位置)的字符串为小写
     *
     * @param source 源字符串
     * @param start  开始转换位置
     * @param end    结束转换位置
     * @return 转换之后的字符串
     */
    public static String toLowerCase(String source, int start, int end) {
        if (isEmpty(source)) {
            return null;
        }
        String str2 = source.substring(start, end);
        str2 = str2.toLowerCase();
        if (end < source.length()) {
            source = source.substring(0, start) + str2 + source.substring(end);
        } else {
            source = str2;
        }
        return source;
    }

    /**
     * 转换字符串为小写
     *
     * @param source 源字符串
     * @return 转换之后的字符串
     */
    public static String toLowerCase(String source) {
        if (isEmpty(source)) {
            return null;
        }
        return source.toLowerCase();
    }

    /**
     * 转换${start}开始${end}结束(不包括${end}位置)的字符串为大写
     *
     * @param source 源字符串
     * @param start  开始转换位置
     * @param end    结束转换位置
     * @return 转换之后的字符串
     */
    public static String toUpperCase(String source, int start, int end) {
        if (isEmpty(source)) {
            return null;
        }
        String str2 = source.substring(start, end);
        str2 = str2.toUpperCase();
        if (end < source.length()) {
            source = source.substring(0, start) + str2 + source.substring(end);
        } else {
            source = str2;
        }
        return source;
    }

    /**
     * 转换字符串为小写
     *
     * @param source 源字符串
     * @return 转换之后的字符串
     */
    public static String toUpperCase(String source) {
        if (isEmpty(source)) {
            return null;
        }
        return source.toUpperCase();
    }

    /**
     * byte[]转String
     *
     * @param bytes byte数组
     * @return UTF-8编码的字符串
     */
    public static String byteArrayToString(byte[] bytes) {
        String str = null;
        try {
            str = new String(bytes, Charset.forName("UTF-8"));
        } catch (Exception e) {
            return null;
        }
        return str;
    }

    /**
     * 拆分字符串
     *
     * @param source 源字符串
     * @param regex  分界正则表达式
     * @return 拆分之后的数组
     */
    public static String[] split(String source, String regex) {
        if (source == null || regex == null) {
            return null;
        }
        return source.split(regex);
    }

    /**
     * 剔除所有的空格
     * 例子：excludeBlank("   str1  str2     str3      str4 str5   str6");   结果：[str1, str2, str3, str4, str5, str6]
     *
     * @param str 需要剔除的字符串
     * @return 将每个字符串放入list中
     */
    public static List<String> excludeBlank(String str) {
        String[] split = str.split(" +");
        List<String> list = new ArrayList<>();
        for (String s : split) {
            String trim = s.trim();
            if ("".equals(trim)) {
                continue;
            }
            list.add(s.trim());
        }
        return list;
    }

    /**
     * 使用站位符拼接字符串
     * 格式：sdsdf{}jsk{}sss{}
     *
     * @param format   有占位符的字符串
     * @param argArray 占位符上具体的值
     * @return 拼接之后的字符串
     */
    public static String format(String format, Object... argArray) {
        if (format == null || argArray == null) {
            return "";
        }
        StringBuilder sbuf = new StringBuilder(format.length() + 50);
        int index = 0;
        for (int i = 0; i < argArray.length; i++) {
            int j = format.indexOf("{}", index);
            if (j == -1) {
                sbuf.append(format, index, format.length());
                ArrayFormatCommon.deeplyAppendParameter(sbuf, argArray[i]);
                return sbuf.toString();
            } else {
                sbuf.append(format, index, j);
                ArrayFormatCommon.deeplyAppendParameter(sbuf, argArray[i]);
                index = j + 2;
            }
        }
        sbuf.append(format, index, format.length());
        return sbuf.toString();
    }

    static class ArrayFormatCommon {
        private static void deeplyAppendParameter(StringBuilder sbuf, Object o) {
            if (o == null) {
                sbuf.append("null");
            } else {
                if (!o.getClass().isArray()) {
                    sbuf.append(o.toString());
                } else if (o instanceof boolean[]) {
                    booleanArrayAppend(sbuf, (boolean[]) o);
                } else if (o instanceof byte[]) {
                    byteArrayAppend(sbuf, (byte[]) o);
                } else if (o instanceof char[]) {
                    charArrayAppend(sbuf, (char[]) o);
                } else if (o instanceof short[]) {
                    shortArrayAppend(sbuf, (short[]) o);
                } else if (o instanceof int[]) {
                    intArrayAppend(sbuf, (int[]) o);
                } else if (o instanceof long[]) {
                    longArrayAppend(sbuf, (long[]) o);
                } else if (o instanceof float[]) {
                    floatArrayAppend(sbuf, (float[]) o);
                } else if (o instanceof double[]) {
                    doubleArrayAppend(sbuf, (double[]) o);
                } else if (o instanceof String[]) {
                    stringArrayAppend(sbuf, (String[]) o);
                }
            }
        }

        static boolean isEscapedDelimeter(String messagePattern, int delimeterStartIndex) {
            if (delimeterStartIndex == 0) {
                return false;
            } else {
                char potentialEscape = messagePattern.charAt(delimeterStartIndex - 1);
                return potentialEscape == '\\';
            }
        }

        private static void booleanArrayAppend(StringBuilder sbuf, boolean[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void byteArrayAppend(StringBuilder sbuf, byte[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void charArrayAppend(StringBuilder sbuf, char[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void shortArrayAppend(StringBuilder sbuf, short[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void intArrayAppend(StringBuilder sbuf, int[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void longArrayAppend(StringBuilder sbuf, long[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void floatArrayAppend(StringBuilder sbuf, float[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void doubleArrayAppend(StringBuilder sbuf, double[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

        private static void stringArrayAppend(StringBuilder sbuf, String[] a) {
            sbuf.append('[');
            int len = a.length;

            for (int i = 0; i < len; ++i) {
                sbuf.append(a[i]);
                if (i != len - 1) {
                    sbuf.append(", ");
                }
            }

            sbuf.append(']');
        }

    }

    /**
     * 实现js的escape函数
     *
     * @param input 待传入字符串
     * @return 转换之后的字符串
     */
    private static String escape(String input) {
        int len = input.length();
        int i;
        char j;
        StringBuilder result = new StringBuilder();
        result.ensureCapacity(len * 6);
        for (i = 0; i < len; i++) {
            j = input.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j) || Character.isUpperCase(j)) {
                result.append(j);
            } else if (j < 256) {
                result.append("%");
                if (j < 16) {
                    result.append("0");
                }
                result.append(Integer.toString(j, 16));
            } else {
                result.append("%u");
                result.append(Integer.toString(j, 16));
            }
        }
        return result.toString();

    }

    /**
     * 实现js的unescape函数
     *
     * @param input 待传入字符串
     * @return 转换之后的字符串
     */
    private static String unescape(String input) {
        int len = input.length();
        StringBuilder result = new StringBuilder();
        result.ensureCapacity(len);
        int lastPos = 0, pos = 0;
        char ch;
        while (lastPos < len) {
            pos = input.indexOf("%", lastPos);
            if (pos == lastPos) {
                if (input.charAt(pos + 1) == 'u') {
                    ch = (char) Integer.parseInt(input.substring(pos + 2, pos + 6), 16);
                    result.append(ch);
                    lastPos = pos + 6;
                } else {
                    ch = (char) Integer.parseInt(input.substring(pos + 1, pos + 3), 16);
                    result.append(ch);
                    lastPos = pos + 3;
                }
            } else {
                if (pos == -1) {
                    result.append(input.substring(lastPos));
                    lastPos = len;
                } else {
                    result.append(input.substring(lastPos, pos));
                    lastPos = pos;
                }
            }
        }
        return result.toString();
    }

    /**
     * Unicode编码字符串转中文字符串
     *
     * @param input 待传入字符串
     * @return 转换之后的字符串
     */
    public static String toGb2312(String input) {
        input = input.trim().replaceAll("(?i)\\\\u", "%u");
        return unescape(input);
    }

    /**
     * Unicode编码字符串转中文字符串
     *
     * @param input 待传入字符串
     * @return 转换之后的字符串
     */
    public static String toChinese(String input) {
        return toGb2312(input);
    }

    /**
     * 中文字符串转Unicode编码字符串
     *
     * @param input 待传入字符串
     * @return 转换之后的字符串
     */
    public static String toUnicode(String input) {
        input = input.trim();
        String output = escape(input).toLowerCase().replace("%u", "\\u");
        return output.replaceAll("(?i)%7b", "{").replaceAll("(?i)%7d", "}").replaceAll("(?i)%3a", ":")
                .replaceAll("(?i)%2c", ",").replaceAll("(?i)%27", "'").replaceAll("(?i)%22", "\"")
                .replaceAll("(?i)%5b", "[").replaceAll("(?i)%5d", "]").replaceAll("(?i)%3D", "=")
                .replaceAll("(?i)%20", " ").replaceAll("(?i)%3E", ">").replaceAll("(?i)%3C", "<")
                .replaceAll("(?i)%3F", "?").replaceAll("(?i)%5c", "\\");
    }

}
