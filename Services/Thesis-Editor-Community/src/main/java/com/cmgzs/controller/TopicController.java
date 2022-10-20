package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.Topic;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.base.TableDataInfo;
import com.cmgzs.service.TopicService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.vo.TopicVO;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/17
 */
@RequiredToken
@RestController
@RequestMapping("/community/topic")
public class TopicController extends BaseController {

    @Resource
    private TopicService topicService;

    /**
     * 获取发布的帖子列表
     *
     * @param topicVO
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public TableDataInfo getTopics(TopicVO topicVO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicVO, topic);
        return PageUtils.getDataTable(topicService.getTopics(topic));
    }

    /**
     * 获取详情
     *
     * @param topicId
     * @return
     */
    @RequestMapping(value = "/{topicId}", method = RequestMethod.GET)
    public ApiResult getTopicDetail(@PathVariable String topicId) {
        return toResult(topicService.getTopicDetail(topicId));
    }

    /**
     * 发布帖子
     *
     * @param topicVO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ApiResult publishTopic(@RequestBody TopicVO topicVO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicVO, topic);
        int publishTopic = topicService.publishTopic(topic);
        if (publishTopic != 1) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }

    /**
     * 修改帖子
     *
     * @param topicVO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ApiResult updateTopic(@RequestBody TopicVO topicVO) {
        Topic topic = new Topic();
        BeanUtils.copyProperties(topicVO, topic);
        int updateTopic = topicService.updateTopic(topic);
        if (updateTopic != 1) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }

    /**
     * 删除帖子
     *
     * @param topicId
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    public ApiResult deleteTopic(@RequestParam String topicId) {
        int deleteTopic = topicService.deleteTopic(topicId);
        if (deleteTopic != 1) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }

    /**
     * 添加浏览次数
     *
     * @param topicId
     * @return
     */
    @RequiredToken(value = false)   //不需要校验即可访问
    @RequestMapping(value = "/incrBrowse", method = RequestMethod.GET)
    public ApiResult incrBrowse(@RequestParam String topicId) {
        long incrBrowse = topicService.incrBrowse(topicId);
        if (incrBrowse != 1) {
            return ApiResult.error();
        }
        return ApiResult.success();
    }
}
