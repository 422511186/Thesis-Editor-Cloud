package com.cmgzs.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务接口 定义接口行为规范
 *
 * @author huangzhenyu
 * @date 2022/9/30
 */
public interface FileUploadService {

    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    String uploadFile(MultipartFile file);

    /**
     * 文件下载
     *
     * @param fileId 文件Id
     * @return
     */
    String downloadFile(String fileId);

    /**
     * 文件删除
     *
     * @param fileId 文件Id
     * @return
     */
    String deleteFile(String fileId);
}
