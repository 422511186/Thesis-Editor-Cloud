package com.cmgzs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class CommentVo implements Serializable {

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 所有的二级评论
     */
    private List<CommentVo> commentVOList;
}
