package com.cmgzs.service;

import com.cmgzs.domain.CommentFirst;
import com.cmgzs.vo.CommentVo;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
public interface CommentFirstService {

    /**
     * 获取评论列表
     *
     * @param topicId 帖子Id
     * @return
     */
    List<CommentVo> getList(String topicId);

    /**
     * 新增一级评论
     *
     * @param commentFirst
     * @return
     */
    int insertCommentFirst(CommentFirst commentFirst);

    /**
     * 删除一级评论
     *
     * @param commentId 一级评论的主键
     * @return
     */
    int deleteCommentFirst(String commentId);
}
