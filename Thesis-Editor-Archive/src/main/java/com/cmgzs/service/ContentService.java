package com.cmgzs.service;

import com.cmgzs.domain.Content;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/9/24
 */
public interface ContentService {

    /**
     * 打开文档
     *
     * @param archiveId 文档id
     * @return 结果
     */
    List<Content> openArchive(String archiveId);

    /**
     * 修改和删除的统一入口
     *
     * @param contents 待操作的content
     * @return 结果
     */
    int updateContents(List<Content> contents);

    /**
     * 分块修改 （单个content或者可以按页码）
     * 修改文档内容  -- 修改包括update和insert
     *
     * @param contents 需要修改的content
     * @return 结果
     */
    int saveContents(List<Content> contents);

    /**
     * @param contentIds 需要删除的content ids
     * @return 结果
     */
    int deleteContents(List<String> contentIds);
}
