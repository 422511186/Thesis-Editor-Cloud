package com.cmgzs.service;

import com.cmgzs.domain.Comment;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
public interface CommentService {

    /**
     * 发布评论
     *
     * @param commentPo
     * @return
     */
    int publishComment(Comment commentPo);

    /**
     * 删除评论
     *
     * @return
     */
    int deleteComment(String commentId);
}
