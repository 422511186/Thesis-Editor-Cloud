package com.cmgzs.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 消息拉取表
 *
 * @author huangzhenyu
 * @date 2022/10/21
 */
@Data
public class MessagePull implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键Id
     */
    private String messagePullId;
    /**
     * 拉取消息的用户id
     */
    private String userId;
    /**
     * 发布消息的用户Id
     */
    private String publishUserId;
    /**
     * 消息Id
     */
    private String messageId;
    /**
     * 是否已读
     */
    private String isRead;
    /**
     * 是否删除
     */
    private String isDelete;
}
