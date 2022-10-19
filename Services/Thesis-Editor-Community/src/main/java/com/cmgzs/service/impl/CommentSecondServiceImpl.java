package com.cmgzs.service.impl;

import com.cmgzs.domain.CommentSecond;
import com.cmgzs.domain.UserContext;
import com.cmgzs.mapper.CommentSecondMapper;
import com.cmgzs.service.CommentSecondService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
@Service
public class CommentSecondServiceImpl implements CommentSecondService {

    @Resource
    private CommentSecondMapper commentSecondMapper;

    /**
     * 查询二级评论列表
     *
     * @param commentPid 一级评论Id
     * @return
     */
    @Override
    public List<CommentSecond> getList(String commentPid) {
        PageUtils.startPage();
        return commentSecondMapper.selectAll(commentPid);
    }

    /**
     * 新增二级评论
     *
     * @param commentSecond
     * @return
     */
    @Override
    public int insertCommentSecond(CommentSecond commentSecond) {
        commentSecond.setCommentId(String.valueOf(SnowFlakeUtil.getSnowFlakeId()));
        commentSecond.setUserId(UserContext.getUserId());
        commentSecond.setCreateTime(LocalDateTime.now());
        commentSecond.setIsDelete("0");
        return commentSecondMapper.insert(commentSecond);
    }

    /**
     * 删除二级评论
     *
     * @param commentId 二级评论的主键Id
     * @return
     */
    @Override
    public int deleteCommentSecond(String commentId) {
        return commentSecondMapper.deleteByPrimaryKey(commentId);
    }
}
