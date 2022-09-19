package com.cmgzs.controller;

import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.domain.Document;
import com.cmgzs.service.DocumentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/document")
public class DocumentController extends BaseController {
    @Resource
    private DocumentService documentService;

    /**
     * 获取当前用户创建的的文档项目列表
     *
     * @return 结果
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResult getList() {
        return ApiResult.success(documentService.getDocuments());
    }

    /**
     * 获取id对应文档的详细信息
     *
     * @return 结果
     */
    @RequestMapping(value = "/{uid}", method = RequestMethod.GET)
    public ApiResult getDocumentById(@PathVariable String uid) {
        return ApiResult.success(documentService.getDocumentById(uid));
    }

    /**
     * 创建项目
     *
     * @param document 项目名称和项目类型
     * @return 结果
     */
    @PostMapping
    public ApiResult create(@RequestBody Document document) throws InterruptedException {
        return toResult(documentService.createDocument(document));
    }


    /**
     * 删除项目
     *
     * @param uid 项目id
     * @return 结果
     */
    @DeleteMapping("/{uid}")
    public ApiResult delete(@PathVariable String uid) {
        return toResult(documentService.deleteDocument(uid));
    }

    /**
     * 修改文档项目
     *
     * @param document 项目名称和项目类型
     * @return 结果
     */
    @PutMapping
    public ApiResult update(@RequestBody Document document) throws InterruptedException {
        return toResult(null);
    }
}
