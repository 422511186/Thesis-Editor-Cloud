package com.cmgzs.service;

import com.cmgzs.domain.Topic;
import com.cmgzs.vo.TopicVO;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
public interface TopicService {

    /**
     * 获取发布的帖子列表
     *
     * @param topicPo
     * @return
     */
    List<TopicVO> getTopics(Topic topicPo);

    /**
     * 获取详情
     *
     * @param topicId
     * @return
     */
    TopicVO getTopicDetail(String topicId);

    /**
     * 发布帖子
     *
     * @param topicPo
     * @return
     */
    int publishTopic(Topic topicPo);

    /**
     * 修改帖子
     *
     * @param topicPo
     * @return
     */
    int updateTopic(Topic topicPo);

    /**
     * 删除帖子
     *
     * @param topicId
     * @return
     */
    int deleteTopic(String topicId);

    /**
     * 添加浏览次数
     *
     * @param topicId
     * @return
     */
    int incrBrowse(String topicId);

}
