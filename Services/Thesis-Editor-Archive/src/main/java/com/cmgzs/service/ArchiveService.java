package com.cmgzs.service;

import com.cmgzs.domain.Archive;

public interface ArchiveService {

    /**
     * 创建文档
     * @param archive 文档名称
     * @return 结果
     */
    boolean createDocument(Archive archive) throws InterruptedException;

    /**
     * 删除文档
     * @param uid 文档id
     * @return 结果
     */
    boolean deleteDocument(String uid);

    /**
     * 获取id对应文档的详细信息
     *
     * @param Id 文档文档id
     * @return 结果
     */
    Archive getDocumentById(String Id);

    /**
     * 获取文档列表(当前用户)
     *
     * @return 结果
     */
    Object getDocuments();


    /**
     * 修改文档对象
     *
     * @param archive 参数
     * @return 结果
     */
    int updateDocumentById(Archive archive);

}
