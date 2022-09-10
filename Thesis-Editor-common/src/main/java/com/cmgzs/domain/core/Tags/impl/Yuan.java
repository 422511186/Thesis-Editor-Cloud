package com.cmgzs.domain.core.Tags.impl;

import com.cmgzs.domain.core.Tags.Tag;
import lombok.Data;

/**
 * 块标签
 * \begin{}
 * ```
 * \end{}
 */
@Data
public class Yuan implements Tag {
    private static final long serialVersionUID = 1L;
    private final String tagName = "\\begin{class_name}\n" +
            "content \n" +
            "\\end{class_name}\n";
    /**
     * 类型
     */
    private String class_name;
    /**
     * 内容段
     */
    private String content;

    public Yuan(String class_name, String content) {
        this.class_name = class_name;
        this.content = content;
    }

    /**
     * 替换参数占位符，返回带参数的标签字符串
     *
     * @return 经过替换后的字符串
     */
    @Override
    public String getTagString() {
        return tagName.replace("class_name", class_name).replace("content", content);
    }

}
