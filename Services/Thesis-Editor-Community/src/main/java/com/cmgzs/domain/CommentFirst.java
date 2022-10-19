package com.cmgzs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 一级评论
 *
 * @author huangzhenyu
 * @date 2022/10/18
 */
@Data
public class CommentFirst implements Serializable {

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
     * 是否删除
     */
    private String isDelete;
}
