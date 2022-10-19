package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.CommentFirst;
import com.cmgzs.domain.CommentSecond;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.base.TableDataInfo;
import com.cmgzs.exception.CustomException;
import com.cmgzs.service.CommentFirstService;
import com.cmgzs.service.CommentSecondService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.text.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(method = RequestMethod.GET)
    public TableDataInfo getComments(@RequestParam String topicId) {
        if (StringUtils.isEmpty(topicId)) {
            throw new CustomException("topicId不能为空");
        }
        return PageUtils.getDataTable(commentFirstService.getList(topicId));
    }

    /**
     * 发表一级评论
     *
     * @param commentFirst
     * @return 结果
     */
    @RequestMapping(value = "/first", method = RequestMethod.POST)
    public ApiResult insertCommentFirst(@RequestBody CommentFirst commentFirst) {
        return toResult(commentFirstService.insertCommentFirst(commentFirst));
    }

    /**
     * 删除一级评论
     *
     * @param commentId 一级评论Id
     * @return
     */
    @RequestMapping(value = "/first", method = RequestMethod.DELETE)
    public ApiResult deleteCommentFirst(@RequestParam String commentId) {
        if (StringUtils.isEmpty(commentId)) {
            throw new CustomException("commentId不能为空");
        }
        return toResult(commentFirstService.deleteCommentFirst(commentId));
    }


    /**
     * 查询二级评论列表
     *
     * @param commentPid 一级评论Id
     * @return
     */
    @RequestMapping(value = "/second", method = RequestMethod.GET)
    public TableDataInfo getCommentSeconds(@RequestParam String commentPid) {
        if (StringUtils.isEmpty(commentPid)) {
            throw new CustomException("commentPid不能为空");
        }
        return PageUtils.getDataTable(commentSecondService.getList(commentPid));
    }

    /**
     * 发表二级评论
     *
     * @param commentSecond
     * @return
     */
    @RequestMapping(value = "/second", method = RequestMethod.POST)
    public ApiResult insertCommentSecond(@RequestBody CommentSecond commentSecond) {
        return toResult(commentSecondService.insertCommentSecond(commentSecond));
    }

    /**
     * 删除二级评论
     *
     * @param commentId 二级评论的主键Id
     * @return
     */
    @RequestMapping(value = "/second", method = RequestMethod.DELETE)
    public ApiResult deleteCommentSecond(@RequestParam String commentId) {
        if (StringUtils.isEmpty(commentId)) {
            throw new CustomException("commentId不能为空");
        }
        return toResult(commentSecondService.deleteCommentSecond(commentId));
    }

}
