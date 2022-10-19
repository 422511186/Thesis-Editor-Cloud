package com.cmgzs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.domain.CommentFirst;
import com.cmgzs.domain.UserContext;
import com.cmgzs.exception.CustomException;
import com.cmgzs.mapper.CommentFirstMapper;
import com.cmgzs.mapper.CommentSecondMapper;
import com.cmgzs.service.CommentFirstService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import com.cmgzs.vo.CommentVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
@Service
public class CommentFirstServiceImpl implements CommentFirstService {

    @Resource
    private CommentFirstMapper commentFirstMapper;
    @Resource
    private CommentSecondMapper commentSecondMapper;

    /**
     * 获取评论列表
     *
     * @param topicId 帖子Id
     * @return
     */
    @Override
    public List<CommentVo> getList(String topicId) {
        PageUtils.startPage();
        List<CommentFirst> commentFirsts = commentFirstMapper.selectAll(topicId);
        List<CommentVo> commentVos = JSONObject.parseArray(JSON.toJSONString(commentFirsts), CommentVo.class);

        commentVos.forEach(e -> {
            PageUtils.startPage(1, 5, "create_time");//二级评论分页
            e.setCommentSeconds(commentSecondMapper.selectAll(e.getCommentId()));
        });

        return commentVos;
    }

    /**
     * 新增一级评论
     *
     * @param commentFirst 一级评论Po
     * @return
     */
    @Override
    public int insertCommentFirst(CommentFirst commentFirst) {
        commentFirst.setCommentId(String.valueOf(SnowFlakeUtil.getSnowFlakeId()));
        commentFirst.setUserId(UserContext.getUserId());
        commentFirst.setCreateTime(LocalDateTime.now());
        commentFirst.setIsDelete("0");
        return commentFirstMapper.insert(commentFirst);
    }

    /**
     * 删除一级评论
     *
     * @param commentId 一级评论的主键
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCommentFirst(String commentId) {
        CommentFirst selectByPrimaryKey = commentFirstMapper.selectByPrimaryKey(commentId);
        if (selectByPrimaryKey == null) {
            throw new CustomException("该评论不存在");
        }
        commentSecondMapper.deleteByCommentPid(commentId);
        return commentFirstMapper.deleteByPrimaryKey(commentId);
    }
}
