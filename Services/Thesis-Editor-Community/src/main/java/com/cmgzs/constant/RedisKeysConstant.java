package com.cmgzs.constant;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
public class RedisKeysConstant {

    /**
     * 浏览次数
     */
    public static String TOPIC_BROWSE = "topic::browse::";
    /**
     * 帖子 赞  -- 计数
     * [userId]
     */
    public static String TOPIC_FABULOUS = "topic::fabulous::";
    /**
     * 评论 赞
     * [userId]
     */
    public static String COMMENT_FABULOUS = "comment::fabulous::";
    
}
