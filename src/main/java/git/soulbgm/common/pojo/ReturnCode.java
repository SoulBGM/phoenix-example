package git.soulbgm.common.pojo;

/**
 * 前端返回统一状态code和消息
 *
 * @author SoulBGM
 * @version V1.0
 * @date 2020-07-30 13:48:42
 */
public enum ReturnCode {

    SUCCESS(200,"成功"),

    SAVE_FAIL(500, "添加失败"),

    REMOVE_FAIL(500, "删除失败"),

    UPDATE_FAIL(500, "修改失败"),

    INVALID_FAIL(404, "此ID不存在"),

    FIND_FAIL(500, "查询失败"),

    // TODO 登录相关返回信息

    LOGIN_SUCCESS(200, "登录成功"),

    LOGOUT_SUCCESS(200, "注销成功"),

    HAS_LOGGED(200, "已登录"),

    LOGIN_FAIL(403, "登录失败"),

    UNKNOWN_ACCOUNT(401, "未知账户"),

    ACCOUNT_LOCKED(403, "账户已锁定"),

    USERNAME_OR_PASSWORD_IS_INCORRECT(403, "用户名或密码不正确"),

    TOO_MANY_INCORRECT_USERNAME_OR_PASSWORD(403, "用户名或密码错误次数过多"),

    // TODO 权限相关返回信息

    NO_SUCH_PERMISSION(403, "没有此权限"),

    NO_LOGIN(401, "请先登录"),

    // 压底的(●'◡'●)
    END(200,"");

    int code;
    String msg;

    ReturnCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
