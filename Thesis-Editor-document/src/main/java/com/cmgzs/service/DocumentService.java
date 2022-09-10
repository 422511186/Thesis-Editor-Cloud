package com.cmgzs.service;

import com.cmgzs.domain.document.Document;

import java.util.List;

public interface DocumentService {

    /**
     * 创建项目
     * @param document 项目名称
     * @return 结果
     */
    boolean createDocument(Document document) throws InterruptedException;

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
    Document getDocumentById(String Id);

    /**
     * 获取全部的项目名称(当前用户)
     *
     * @return 结果
     */
    List<Document> getDocuments();


    /**
     * 修改文档对象
     *
     * @param documents 参数
     * @return 结果
     */
    int updateDocumentById(List<Document> documents);

}
