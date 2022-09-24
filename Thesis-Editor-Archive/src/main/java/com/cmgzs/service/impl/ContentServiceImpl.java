package com.cmgzs.service.impl;

import com.cmgzs.domain.Content;
import com.cmgzs.mapper.ContentMapper;
import com.cmgzs.service.ContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/9/24
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Resource
    private ContentMapper contentMapper;


    /**
     * 打开文档
     *
     * @param archiveId 文档id
     * @return 结果
     */
    @Override
    public List<Content> openArchive(String archiveId) {
        return null;
    }

    /**
     * 修改和删除的统一入口
     *
     * @param contents 待操作的content
     * @return 结果
     */
    @Override
    public int updateContents(List<Content> contents) {
        return 0;
    }

    /**
     * 分块修改 （单个content或者可以按页码）
     * 修改文档内容  -- 修改包括update和insert
     *
     * @param contents 需要修改的content
     * @return 结果
     */
    @Override
    public int saveContents(List<Content> contents) {
        return 0;
    }

    /**
     * @param contentIds 需要删除的content ids
     * @return 结果
     */
    @Override
    public int deleteContents(List<String> contentIds) {
        return 0;
    }
}
