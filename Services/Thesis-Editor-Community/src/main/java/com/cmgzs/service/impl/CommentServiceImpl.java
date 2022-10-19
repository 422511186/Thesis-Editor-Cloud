package com.cmgzs.service.impl;

import com.cmgzs.domain.Comment;
import com.cmgzs.domain.UserContext;
import com.cmgzs.mapper.CommentMapper;
import com.cmgzs.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author huangzhenyu
 * @date 2022/10/18
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    /**
     * 发布评论
     *
     * @param commentPo
     * @return
     */
    @Override
    public int publishComment(Comment commentPo) {
        commentPo.setUserId(UserContext.getUserId());
        commentPo.setCreateTime(LocalDateTime.now());
        return commentMapper.insertComment(commentPo);
    }

    /**
     * 删除评论
     *
     * @param commentId
     * @return
     */
    @Override
    public int deleteComment(String commentId) {
        return 0;
    }
}
