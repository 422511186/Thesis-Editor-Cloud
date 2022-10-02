package com.cmgzs.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文件记录表映射实体
 *
 * @author huangzhenyu
 * @date 2022/10/1
 */
@Data
public class FilesUpload {

    /**
     * 文件主键
     */
    private String fileKey;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件存储路径
     */
    private String filePath;
    /**
     * 文件状态
     */
    private String status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 创建人
     */
    private String createBy;
}
