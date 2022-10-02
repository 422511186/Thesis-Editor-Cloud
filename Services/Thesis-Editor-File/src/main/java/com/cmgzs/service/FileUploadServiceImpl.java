package com.cmgzs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传服务
 * 本地文件系统实现
 *
 * @author huangzhenyu
 * @date 2022/9/30
 */
@Primary
@Service(value = "fileServiceImpl")
public class FileUploadServiceImpl implements FileUploadService {

    /**
     * 本地上传文件目录
     */
    @Value(value = "${File.upload}")
    private String upload;

    /**
     * 本地上传文件目录
     */
    @Value(value = "${File.temps}")
    private String temps;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file) {
        return null;
    }

    /**
     * 文件下载
     *
     * @param fileId 文件Id
     * @return
     */
    @Override
    public String downloadFile(String fileId) {
        return null;
    }

    /**
     * 文件删除
     *
     * @param fileId 文件Id
     * @return
     */
    @Override
    public String deleteFile(String fileId) {
        return null;
    }
}
