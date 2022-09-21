package com.cmgzs.constant;

/**
 * 通用常量信息
 *
 * @author hzy
 */
public class Constants {

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * access_token
     */
    public static final String LOGIN_TOKEN_KEY = "access_token:";

    /**
     * refresh_token
     */
    public static final String REFRESH_TOKEN = "refresh_token:";


    /**
     * access_token 令牌前缀(header )
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀 （Claims）
     */
    public static final String LOGIN_USER_KEY = "login_user_key";

    public static final int access_token_expire = -1;

    public static final int refresh_token_expire = -2;


}
