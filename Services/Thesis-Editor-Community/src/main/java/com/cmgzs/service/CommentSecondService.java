package com.cmgzs.service;

import com.cmgzs.domain.CommentSecond;
import com.cmgzs.vo.CommentSecondVo;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
public interface CommentSecondService {

    /**
     * 查询二级评论列表
     *
     * @param commentPid 一级评论Id
     * @return
     */
    List<CommentSecondVo> getList(String commentPid);

    /**
     * 新增二级评论
     *
     * @param commentSecond
     * @return
     */
    int insertCommentSecond(CommentSecond commentSecond);

    /**
     * 删除二级评论
     *
     * @param commentId 二级评论的主键Id
     * @return
     */
    int deleteCommentSecond(String commentId);
}
