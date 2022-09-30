package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.Archive;
import com.cmgzs.service.ArchiveService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/archive")
@RequiredToken(value = true)
public class ArchiveController extends BaseController {
    @Resource
    private ArchiveService archiveService;

    /**
     * 获取当前用户创建的的文档项目列表
     *
     * @return 结果
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult getList() {
        return ApiResult.success(archiveService.getDocuments());
    }

    /**
     * 获取id对应文档的详细信息
     *
     * @return 结果
     */
    @RequestMapping(value = "/{archiveId}", method = RequestMethod.GET)
    public ApiResult getDocumentById(@PathVariable String archiveId) {
        return ApiResult.success(archiveService.getDocumentById(archiveId));
    }

    /**
     * 创建项目
     *
     * @param archive 项目名称和项目类型
     * @return 结果
     */
    @PostMapping
    public ApiResult create(@RequestBody Archive archive) throws InterruptedException {
        return toResult(archiveService.createDocument(archive));
    }


    /**
     * 删除项目
     *
     * @param archiveId 项目id
     * @return 结果
     */
    @DeleteMapping("/{archiveId}")
    public ApiResult delete(@PathVariable String archiveId) {
        return toResult(archiveService.deleteDocument(archiveId));
    }

    /**
     * 修改文档项目
     *
     * @param archive 文档类
     * @return 结果
     */
    @PutMapping
    public ApiResult update(@RequestBody Archive archive) {
        archiveService.updateDocumentById(archive);
        return success();
    }
}
