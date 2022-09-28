package com.cmgzs.service.impl;


import com.cmgzs.domain.Archive;
import com.cmgzs.mapper.ArchiveMapper;
import com.cmgzs.service.LaTexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Service
@Slf4j
public class LaTexServiceImpl implements LaTexService {

    @Resource
    private ArchiveMapper documentMapper;


    /**
     * 项目工作文件位置
     */
    @Value(value = "${Thesis-Editor.workdir.path}")
    private String path;

    /**
     * 将Document文档对象整合为代码段，保存编译。
     *
     * @param document 文档对象
     * @return 代码段
     */
    @Override
    public String convert(Archive document) throws IllegalAccessException {
        return null;
    }

    /**
     * 写入文件信息(格式——Tex)
     *
     * @param Id  项目id
     * @param Tex 文件内容
     * @return 操作是否成功
     */
    @Override
    public File Save(String Id, String Tex) {

        return null;
    }

    /**
     * 编译Tex
     *
     * @param Id 文件名字
     * @return 编译完成的PDF文件名称
     */
    @Override
    public boolean Compile(String Id) {
        return false;
    }

}
