package com.cmgzs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.constant.RedisKeysConstant;
import com.cmgzs.domain.Topic;
import com.cmgzs.domain.UserContext;
import com.cmgzs.exception.CustomException;
import com.cmgzs.feign.UserinfoFeign;
import com.cmgzs.mapper.CommentFirstMapper;
import com.cmgzs.mapper.CommentSecondMapper;
import com.cmgzs.mapper.TopicMapper;
import com.cmgzs.service.TopicService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import com.cmgzs.vo.TopicVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
    @Resource
    private UserinfoFeign userinfoFeign;

    /**
     * 获取发布的帖子列表    包含热度
     *
     * @param topicPo
     * @return
     */
    @Override
    public List<TopicVO> getTopics(Topic topicPo) {
        topicPo.setUserId(UserContext.getUserId());
        PageUtils.startPage();
        List<Topic> topics = topicMapper.selectAll(topicPo);
        List<TopicVO> topicVos = JSONObject.parseArray(JSON.toJSONString(topics), TopicVO.class);

        HashSet<String> userIds = new HashSet<>();

        topicVos.forEach(e -> {
            userIds.add(e.getUserId());
        });

        String[] params = userIds.toArray(new String[0]);
        JSONObject resJson = JSONObject.parseObject(JSONObject.toJSONString(userinfoFeign.getNickNames(params)));
        JSONObject data = resJson.getJSONObject("data");
        Map map = data.toJavaObject(Map.class);
        topicVos.forEach(e -> {
            e.setUserInfo(map.get(e.getUserId()));
            //查询浏览信息
            log.info("redis key:{}", RedisKeysConstant.TOPIC_BROWSE + e.getTopicId());
            Object result = redisTemplate.opsForValue().get(RedisKeysConstant.TOPIC_BROWSE + e.getTopicId());
            log.info("result:{}", result);
            long browse = result == null ? 0L : Long.parseLong(result.toString());
            e.setBrowse(browse);
        });

        return topicVos;
    }

    /**
     * 获取详情
     *
     * @param topicId
     * @return
     */
    @Override
    public TopicVO getTopicDetail(String topicId) {
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        if (topic == null) {
            throw new CustomException("该帖子不存在");
        }
        TopicVO topicVO = JSONObject.parseObject(JSON.toJSONString(topic), TopicVO.class);
        JSONObject resJson = JSONObject.parseObject(JSONObject.toJSONString(userinfoFeign.getNickNames(new String[]{topicVO.getUserId()})));
        JSONObject data = resJson.getJSONObject("data");
        Map map = data.toJavaObject(Map.class);
        topicVO.setUserInfo(map.get(topicVO.getUserId()));
        //添加一次浏览量
        topicVO.setBrowse(incrBrowse(topicId));
        return topicVO;
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
    public long incrBrowse(String topicId) {
//        return topicMapper.incrBrowse(topicId);
        String key = RedisKeysConstant.TOPIC_BROWSE + topicId;
        redisTemplate.opsForValue().setIfAbsent(key, 0);
        redisTemplate.opsForValue().increment(key);
        return Long.parseLong(String.valueOf(redisTemplate.opsForValue().get(key)));
    }
}
