package com.cmgzs.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TopicVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 帖子id
     */
    private String topicId;
    /**
     * 帖子分类
     */
    private String type;
    /**
     * 帖子封面
     */
    private String coverPic;
    /**
     * 帖子标题
     */
    private String title;
    /**
     * 帖子内容
     */
    private String content;
    /**
     * 图片地址
     */
    private String pic;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;
    /**
     * 浏览次数
     */
    private Long browse;
    /**
     * 审核状态		0: 待审核	1: 审核通过	2: 审核不通过
     */
    private String auditStatus;
}