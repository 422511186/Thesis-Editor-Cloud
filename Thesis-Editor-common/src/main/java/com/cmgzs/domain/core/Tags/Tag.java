package com.cmgzs.domain.core.Tags;

import java.io.Serializable;

/**
 * Tex标签接口
 */
public interface Tag extends Serializable {
    /**
     * 替换参数占位符，返回带参数的标签字符串
     *
     * @return 经过替换后的字符串
     */
    public String getTagString();

    /**
     * 返回标签替换、填充后的字符串
     *
     * @return 结果
     */
    public default String getTag() {
        return getTagString();
    }
}
