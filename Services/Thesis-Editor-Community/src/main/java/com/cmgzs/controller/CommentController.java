package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.base.TableDataInfo;
import com.cmgzs.exception.CustomException;
import com.cmgzs.service.CommentFirstService;
import com.cmgzs.service.CommentSecondService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.text.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author huangzhenyu
 * @date 2022/10/18
 */
@RequiredToken
@RestController
@RequestMapping("/community/comment")
public class CommentController extends BaseController {
    @Resource
    private CommentFirstService commentFirstService;

    @Resource
    private CommentSecondService commentSecondService;

    /**
     * 获取评论列表
     *
     * @param topicId 帖子Id
     * @return 结果
     */
    public TableDataInfo getComments(@RequestParam String topicId) {
        if (StringUtils.isEmpty(topicId)) {
            throw new CustomException("topicId不能为空");
        }
        return PageUtils.getDataTable(commentFirstService.getList(topicId));
    }


}
