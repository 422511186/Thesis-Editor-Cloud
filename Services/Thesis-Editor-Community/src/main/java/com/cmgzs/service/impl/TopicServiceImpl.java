package com.cmgzs.service.impl;

import com.alibaba.fastjson.JSON;
import com.cmgzs.domain.Topic;
import com.cmgzs.domain.UserContext;
import com.cmgzs.exception.CustomException;
import com.cmgzs.mapper.CommentFirstMapper;
import com.cmgzs.mapper.CommentSecondMapper;
import com.cmgzs.mapper.TopicMapper;
import com.cmgzs.service.TopicService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
@Slf4j
@Service
public class TopicServiceImpl implements TopicService {

    @Resource
    private TopicMapper topicMapper;
    @Resource
    private CommentFirstMapper commentFirstMapper;
    @Resource
    private CommentSecondMapper commentSecondMapper;
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteTopic(String topicId) {
        Topic topic = topicMapper.selectUserIdByTopicId(topicId);
        if (topic == null || topic.getUserId() == null) {
            throw new CustomException("该帖子不存在");
        }
        if (!topic.getUserId().equals(UserContext.getUserId())) {
            throw new CustomException("无权限");
        }

        log.info("删除帖子:{}", JSON.toJSONString(topic));

        // TODO: 2022/10/19 删除两级评论表
        executor.execute(new Runnable() {
            @Override
            public void run() {
                commentFirstMapper.deleteByTopicId(topicId);
                commentSecondMapper.deleteByTopicId(topicId);
            }
        });
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
