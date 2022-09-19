package com.cmgzs.domain;

import com.cmgzs.domain.Tags.Tag;
import lombok.Data;

import java.io.Serializable;

/**
 * 正文
 */
@Data
public class Content implements Comparable<Content>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识（绑定格式）
     */
    private String uid;

    /**
     * 父级id
     */
    private int pid;

    /**
     * 当前id
     */
    private int id;

    /**
     * 标签类型
     */
    private Tag tag;

    /**
     * 内容
     */
    private String body;

    /**
     * 比较器，用于排序列表   --   从小到大
     * 便于后续的层级编译
     *
     * @param o 参数
     * @return 结果
     */
    @Override
    public int compareTo(Content o) {
        return pid - o.getPid() == 0 ? id - o.getId() : pid - o.getPid();
    }
}
