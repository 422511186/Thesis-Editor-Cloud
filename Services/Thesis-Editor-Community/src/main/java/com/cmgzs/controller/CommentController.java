package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.CommentService;
import com.cmgzs.vo.CommentVo;
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
    private CommentService commentService;

    /**
     * 查看帖子下的所有评论
     *
     * @param
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ApiResult getComments() {
        return success();
    }

    /**
     * 发布评论
     *
     * @param commentVo
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ApiResult publishComment(@RequestBody CommentVo commentVo) {

        return success();
    }

    /**
     * 删除评论
     *
     * @return
     */
    @RequestMapping(value = "/{commentId}", method = RequestMethod.DELETE)
    public ApiResult deleteComment(@PathVariable String commentId) {

        return success();
    }

}
