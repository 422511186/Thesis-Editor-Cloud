package com.cmgzs.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Data
public class TopicHot implements Serializable {
    private static final long serialVersionUID = 1L;

    private String hotId;

    private String topicId;

    private Long hotNum;
}
