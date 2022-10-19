package com.cmgzs.vo;

import com.cmgzs.domain.CommentSecond;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论Vo
 *
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class CommentVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 一级评论Id
     */
    private String commentId;
    /**
     * 发表当前评论的用户Id
     */
    private String userId;

    /**
     * 帖子Id
     */
    private String topicId;

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
     * 所有的二级评论
     */
    private List<CommentSecond> commentSeconds;
}
