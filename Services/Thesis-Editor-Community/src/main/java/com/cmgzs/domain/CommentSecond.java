package com.cmgzs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 二级评论
 *
 * @author huangzhenyu
 * @date 2022/10/18
 */
@Data
public class CommentSecond implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 二级评论Id
     */
    private String commentId;

    /**
     * 发表当前评论的用户 Id
     */
    private String userId;

    /**
     * 帖子Id
     */
    private String topicId;

    /**
     * 一级评论的评论id
     */
    private String commentPid;

    /**
     * 被回复的用户id
     */
    private String replyUserId;

    /**
     * 被回复的评论id
     */
    private String replyCommentId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 是否删除		0 = 未删除	1 = 已删除
     */
    private String isDelete;
}
