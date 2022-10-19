package com.cmgzs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 帖子id
     */
    private String topicId;
    /**
     * 发帖的用户id
     */
    private String userId;
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
    /**
     * 是否删除		0: 未删除	1: 已删除
     */
    private String isDelete;
}
