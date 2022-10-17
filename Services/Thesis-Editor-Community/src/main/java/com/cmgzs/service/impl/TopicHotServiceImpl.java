package com.cmgzs.service.impl;

import com.cmgzs.service.RedisService;
import com.cmgzs.service.TopicHotService;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
public class TopicHotServiceImpl implements TopicHotService {
    @Resource
    private RedisService redisService;

    /**
     * 增加某个帖子的热度    +1
     *
     * @param topicId
     * @return
     */
    @Override
    public int incr(String topicId) {
        return 0;
    }

    /**
     * 删除行记录
     *
     * @param TopicId
     * @return
     */
    @Override
    public int deleteTopicHot(String TopicId) {
        return 0;
    }
}
