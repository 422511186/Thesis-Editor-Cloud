package com.cmgzs.domain;


import com.cmgzs.Tags.impl.UsePackage;
import com.cmgzs.enums.DocumentTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 文档项目(mongodb存储结构)
 */
@Data
@Document(value = "Archives")
public class Archive implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 实例（项目）唯一标识
     */
    @Id         /*主键*/
    private String archiveId;

    /**
     * 所有者
     */
    @Indexed    /*索引*/
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
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDateTime;

}