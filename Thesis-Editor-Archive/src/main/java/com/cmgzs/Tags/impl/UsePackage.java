package com.cmgzs.Tags.impl;

import com.cmgzs.Tags.Tag;
import lombok.Data;

import java.util.ArrayList;

@Data
public class UsePackage implements Tag {
    private static final long serialVersionUID = 1L;
    private final String tagName = "\\usepackage[option]{packageName} \n";
    private ArrayList<String> option;
    private String packageName;

    public UsePackage(ArrayList<String> option, String packageName) {
        this.option = option;
        this.packageName = packageName;
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
        return tagName.replace("option", option_str.toString()).replace("packageName", packageName);
    }
}
