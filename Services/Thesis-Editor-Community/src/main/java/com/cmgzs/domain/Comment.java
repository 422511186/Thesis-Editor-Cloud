package com.cmgzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 评论Id
     */
    private String commentId;
    /**
     * 帖子Id
     */
    private String topicId;
    /**
     * 父评论的用户id
     */
    private String userPid;
    /**
     * 父评论的评论id
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
    private LocalDateTime createTime;
    /**
     * 是否删除
     */
    private String isDelete;
}
