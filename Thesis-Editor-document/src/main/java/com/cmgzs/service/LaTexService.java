package com.cmgzs.service;


import com.cmgzs.domain.document.Document;

import java.io.File;

public interface LaTexService {

    /**
     * 将Document文档对象整合为代码，保存编译。
     *
     * @param document 文档对象
     * @return 结果 代码段
     */
    String convert(Document document) throws IllegalAccessException;

    /**
     * 写入文件信息(格式Tex)
     *
     * @param Id 项目id
     * @param Tex 文件内容
     * @return 操作是否成功
     */
    File Save(String Id, String Tex);

    /**
     * 编译Tex
     *
     * @param Id 文档项目
     * @return 编译完成的PDF文件名称
     */
    boolean Compile(String Id);


}
