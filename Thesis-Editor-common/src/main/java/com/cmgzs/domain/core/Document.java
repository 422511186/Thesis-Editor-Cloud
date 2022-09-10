package com.cmgzs.domain.core;


import com.cmgzs.domain.core.Tags.impl.UsePackage;
import com.cmgzs.enums.DocumentTypes;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 文档项目(mongodb存储结构)
 */
@Data
public class Document implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 实例（项目）唯一标识
     */
    /*主键*/
    private String uid;

    /**
     * 所有者
     */
    /*索引*/
    private String auth;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 文档类型
     */
    private DocumentTypes type;

    /**
     * 文档排版配置参数
     */
    private DocumentOption options;

    /**
     * 使用的宏包集合
     */
    private ArrayList<UsePackage> packages;

    /**
     * 正文（标签 + 文字）
     */
    private ArrayList<Content> content;

    /**
     * 正文参数配置
     */
    private ArrayList<ContentStyle> contentStyles;

}