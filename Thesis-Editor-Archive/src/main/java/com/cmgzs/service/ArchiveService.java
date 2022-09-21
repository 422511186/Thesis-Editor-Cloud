package com.cmgzs.service;

import com.cmgzs.domain.Archive;

import java.util.List;

public interface ArchiveService {

    /**
     * 创建项目
     * @param archive 项目名称
     * @return 结果
     */
    boolean createDocument(Archive archive) throws InterruptedException;

    /**
     * 删除项目
     * @param uid 项目id
     * @return 结果
     */
    boolean deleteDocument(String uid);

    /**
     * 通过项目id获取项目详细信息
     *
     * @param Id 文档项目id
     * @return 结果
     */
    Archive getDocumentById(String Id);

    /**
     * 获取全部的项目名称(当前用户)
     *
     * @return 结果
     */
    List<Archive> getDocuments();


    /**
     * 修改文档对象
     *
     * @param archives 参数
     * @return 结果
     */
    int updateDocumentById(List<Archive> archives);

}
