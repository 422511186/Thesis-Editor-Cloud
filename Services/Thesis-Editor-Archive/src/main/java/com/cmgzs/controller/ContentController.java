package com.cmgzs.controller;

import com.cmgzs.annotation.RequiredToken;
import com.cmgzs.domain.Content;
import com.cmgzs.domain.base.ApiResult;
import com.cmgzs.service.ContentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 文档内容
 *
 * @author huangzhenyu
 * @date 2022/9/24
 */
@RestController
@RequestMapping("/archive/content")
@RequiredToken(value = true)
public class ContentController extends BaseController {

    @Resource
    private ContentService contentService;

    /**
     * 打开文档
     *
     * @param archiveId 文档id
     * @return 结果
     */
    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public ApiResult openArchive(@RequestParam String archiveId) {
        return warn();
    }

    /**
     * 保持打开状态（需要续签）
     *
     * @param archiveId 打开的文档id
     * @return 结果
     */
    @RequestMapping(value = "/keepOpen", method = RequestMethod.GET)
    public ApiResult keepOpen(@RequestParam String archiveId) {
        return warn();
    }

    /**
     * 修改和删除的统一入口
     *
     * @param contents 待操作的content
     * @return 结果
     */
    @RequestMapping(value = "/updateContents", method = RequestMethod.PUT)
    public ApiResult updateContents(@RequestBody List<Content> contents) {

        return warn();
    }

    /**
     * 分块修改 （单个content或者可以按页码）
     * 修改文档内容  -- 修改包括update和insert
     *
     * @param contents 需要修改的content
     * @return 结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ApiResult saveContents(@RequestBody List<Content> contents) {
        return warn();
    }

    /**
     * @param contentIds 需要删除的content ids
     * @return 结果
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ApiResult deleteContents(@RequestBody List<String> contentIds) {
        return warn();
    }
}
