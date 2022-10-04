package com.cmgzs.constant;

/**
 * Redis的key
 *
 * @author huangzhenyu
 * @date 2022/9/25
 */
public class RedisConstant {

    /**
     * 文档锁， 保证一段时间内只有当前用户可以打开此文档
     */
    public final static String archive_lock = "archiveId_";
    /**
     * email 验证码
     */
    public final static String email_prefix = "email_prefix";


}
