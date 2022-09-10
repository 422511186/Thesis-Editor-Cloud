package com.cmgzs.domain.document.Tags.impl;



import com.cmgzs.domain.document.Tags.Tag;
import lombok.Data;


/**
 * 正文开始 --  标签
 */
@Data
public class Author implements Tag {
    private static final long serialVersionUID = 1L;

    private final String tagName = "\\author{content}   \n";
    private String content;

    public Author(String content) {
        this.content = content;
    }

    /**
     * 替换参数占位符，返回带参数的标签字符串
     *
     * @return 经过替换后的字符串
     */
    @Override
    public String getTagString() {
        return tagName.replace("content", content);
    }
}
