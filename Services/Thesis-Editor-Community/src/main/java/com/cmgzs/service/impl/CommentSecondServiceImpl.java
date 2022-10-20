package com.cmgzs.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmgzs.domain.CommentSecond;
import com.cmgzs.domain.UserContext;
import com.cmgzs.feign.UserinfoFeign;
import com.cmgzs.mapper.CommentSecondMapper;
import com.cmgzs.service.CommentSecondService;
import com.cmgzs.utils.PageUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import com.cmgzs.vo.CommentSecondVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
@Service
public class CommentSecondServiceImpl implements CommentSecondService {

    @Resource
    private CommentSecondMapper commentSecondMapper;
    @Resource
    private UserinfoFeign userinfoFeign;

    /**
     * 查询二级评论列表
     *
     * @param commentPid 一级评论Id
     * @return
     */
    @Override
    public List<CommentSecondVo> getList(String commentPid) {
        PageUtils.startPage();
        List<CommentSecond> commentSeconds = commentSecondMapper.selectAll(commentPid);
        List<CommentSecondVo> commentSecondVos = JSONObject.parseArray(JSON.toJSONString(commentSeconds), CommentSecondVo.class);

        HashSet<String> userIds = new HashSet<>();

        commentSecondVos.forEach(e -> {
            userIds.add(e.getUserId());
            userIds.add(e.getReplyUserId());
        });

        String[] params = userIds.toArray(new String[0]);
        JSONObject resJson = JSONObject.parseObject(JSONObject.toJSONString(userinfoFeign.getNickNames(params)));
        JSONObject data = resJson.getJSONObject("data");
        Map map = data.toJavaObject(Map.class);

        commentSecondVos.forEach(e -> {
            e.setUserInfo(map.get(e.getUserId()));
            e.setReplyUserInfo(map.get(e.getReplyUserId()));
        });
        return commentSecondVos;
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
