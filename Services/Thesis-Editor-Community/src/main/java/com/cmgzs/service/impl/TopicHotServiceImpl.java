package com.cmgzs.service.impl;

import com.cmgzs.constant.RedisKeysConstant;
import com.cmgzs.service.TopicHotService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
@Service
public class TopicHotServiceImpl implements TopicHotService {

    @Resource
    private RedisTemplate redisTemplate;

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
     * 删除记录
     *
     * @param TopicId
     * @return
     */
    @Override
    public int deleteTopicHot(String TopicId) {
        return 0;
    }
}
