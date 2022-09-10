package com.cmgzs.domain.core.Tags.impl;

import com.cmgzs.domain.core.Tags.Tag;
import lombok.Data;

import java.util.ArrayList;

/**
 * 文档类  --  标签
 */
@Data
public class DocumentClass implements Tag {
    private static final long serialVersionUID = 1L;
    private final String tagName = "\\documentclass[option]{content} \n";
    private ArrayList<String> option;
    private String content;

    public DocumentClass(ArrayList<String> option, String content) {
        this.option = option;
        this.content = content;
    }

    /**
     * 替换参数占位符，返回带参数的标签字符串
     *
     * @return 经过替换后的字符串
     */
    @Override
    public String getTagString() {
        StringBuffer option_str = new StringBuffer();
        if (option != null && option.size() > 0) {
            option.forEach(e -> option_str.append(e).append(","));
            option_str.deleteCharAt(option_str.length() - 1);
        }
        return tagName.replace("option", option_str.toString()).replace("content", content);
    }
}
