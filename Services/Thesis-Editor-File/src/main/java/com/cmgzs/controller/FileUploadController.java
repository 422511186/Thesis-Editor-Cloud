package com.cmgzs.controller;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件存储统一入口
 *
 * @author huangzhenyu
 * @date 2022/9/30
 */
@Controller
@RequestMapping(value = "/fileUpload")
public class FileUploadController {

    @Resource
    private FileUploadService fileUploadService;

    /**
     * 文件上传
     *
     * @param file 上传的文件
     * @return 结果
     */
    @ResponseBody
    @PostMapping(value = "/upload")
    public ApiResult uploadFile(MultipartFile file) {
        return ApiResult.success(fileUploadService.uploadFile(file));
    }

    /**
     * 文件下载
     *
     * @param FileId 文件Id
     * @return 结果
     */
    @GetMapping(value = "/download")
    public ApiResult downloadFile(@RequestParam String FileId) {
        fileUploadService.downloadFile(FileId);
        return ApiResult.success();
    }

    /**
     * 文件删除
     *
     * @param FileId 文件Id
     * @return 结果
     */
    @ResponseBody
    @DeleteMapping(value = "/delete")
    public ApiResult deleteFile(@RequestParam String FileId) {
        return ApiResult.success(fileUploadService.deleteFile(FileId));
    }
}
