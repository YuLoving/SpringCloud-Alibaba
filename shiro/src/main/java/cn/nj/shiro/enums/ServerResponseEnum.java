package cn.nj.shiro.enums;

/**
 * @author ：zty
 * @date ：Created in 2021/4/26 15:03
 * @description ： 系统返回状态枚举与包装函数
 */
public enum ServerResponseEnum {
    /**
     * 成功
     */
    SUCCESS(0, "成功"),
    /**
     * 失败
     */
    ERROR(10, "失败"),
    /**
     * 账号不存在
     */
    ACCOUNT_NOT_EXIST(11, "账号不存在"),
    /**
     * 账号重复
     */
    DUPLICATE_ACCOUNT(12, "账号重复"),
    /**
     * 账号被禁用
     */
    ACCOUNT_IS_DISABLED(13, "账号被禁用"),
    /**
     * 账号或密码错误
     */
    INCORRECT_CREDENTIALS(14, "账号或密码错误"),
    /**
     * 账号未登录
     */
    NOT_LOGIN_IN(15, "账号未登录"),
    /**
     * 没有权限
     */
    UNAUTHORIZED(16, "没有权限");


    /**
     * 状态码
     */
    private final Integer code;
    /**
     * 状态描述
     */
    private final String message;

    ServerResponseEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
