package com.cmgzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class Topic implements Serializable {

    private static final long serialVersionUID = 1L;

    private String topicId;

    private String userId;

    private String type;

    private String coverPic;

    private String title;

    private String content;

    private String pic;

    private LocalDateTime createTime;

    private LocalTime updateTime;

    private String auditStatus;

    private String isDelete;
}
