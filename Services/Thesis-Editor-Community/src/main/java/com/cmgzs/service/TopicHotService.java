package com.cmgzs.service;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
public interface TopicHotService {

    /**
     * 增加某个帖子的热度    +1
     *
     * @param topicId
     * @return
     */
    int incr(String topicId);

    /**
     * 删除记录    删除帖子的时候调用
     *
     * @param TopicId
     * @return
     */
    int deleteTopicHot(String TopicId);

}
