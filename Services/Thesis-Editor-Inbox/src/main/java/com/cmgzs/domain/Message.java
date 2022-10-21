package com.cmgzs.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息汇总记录表
 *
 * @author huangzhenyu
 * @date 2022/10/21
 */
@Data
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 消息Id
     */
    private String messageId;
    /**
     * 发布消息的用户Id
     */
    private String publishUserId;
    /**
     * 接收者的用户Id
     */
    private String acceptUserId;
    /**
     * 消息内容
     */
    private String Content;
    /**
     * 发布时间
     */
    private LocalDateTime createTime;
}
