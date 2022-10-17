package com.cmgzs.service.impl;

import com.cmgzs.domain.Topic;
import com.cmgzs.domain.UserContext;
import com.cmgzs.exception.CustomException;
import com.cmgzs.mapper.TopicMapper;
import com.cmgzs.service.TopicService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicMapper topicMapper;
    @Resource
    private ThreadPoolTaskExecutor executor;
    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 获取发布的帖子列表    包含热度
     *
     * @param topicPo
     * @return
     */
    @Override
    public List<Topic> getTopics(Topic topicPo) {
        topicPo.setUserId(UserContext.getUserId());
        PageUtils.startPage();
        return topicMapper.selectAll(topicPo);
    }

    /**
     * 获取详情
     *
     * @param topicId
     * @return
     */
    @Override
    public Topic getTopicDetail(String topicId) {
        return topicMapper.selectByPrimaryKey(topicId);
    }

    /**
     * 发布帖子
     *
     * @param topicPo
     * @return
     */
    @Override
    public int publishTopic(Topic topicPo) {
        topicPo.setTopicId(SnowFlakeUtil.getSnowFlakeId().toString());
        topicPo.setUserId(UserContext.getUserId());
        topicPo.setAuditStatus("0");
        topicPo.setCreateTime(LocalDateTime.now());
        topicPo.setUpdateTime(LocalDateTime.now());
        topicPo.setBrowse(0L);
        topicPo.setIsDelete("0");

        return topicMapper.insert(topicPo);
    }

    /**
     * 修改帖子
     *
     * @param topicPo
     * @return
     */
    @Override
    public int updateTopic(Topic topicPo) {

        Topic topic = topicMapper.selectUserIdByTopicId(topicPo.getTopicId());
        if (topic == null || topic.getUserId() == null) {
            throw new CustomException("该帖子不存在");
        }
        if (!topic.getUserId().equals(UserContext.getUserId())) {
            throw new CustomException("无权限");
        }
        topicPo.setUserId(UserContext.getUserId());
        topicPo.setIsDelete("0");
        topicPo.setAuditStatus("0");
        topicPo.setUpdateTime(LocalDateTime.now());
        return topicMapper.updateByPrimaryKey(topicPo);
    }

    /**
     * 删除帖子
     *
     * @param topicId
     * @return
     */
    @Override
    public int deleteTopic(String topicId) {
        Topic topic = topicMapper.selectUserIdByTopicId(topicId);
        if (topic == null || topic.getUserId() == null) {
            throw new CustomException("该帖子不存在");
        }
        if (!topic.getUserId().equals(UserContext.getUserId())) {
            throw new CustomException("无权限");
        }
        return topicMapper.deleteByPrimaryKey(topicId);
    }

    /**
     * 添加浏览次数
     *
     * @param topicId
     * @return
     */
    @Override
    public int incrBrowse(String topicId) {
        return topicMapper.incrBrowse(topicId);
    }
}
