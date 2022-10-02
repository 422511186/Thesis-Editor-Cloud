package com.cmgzs.mapper;


import com.cmgzs.domain.FilesUpload;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/1
 */
@Mapper
public interface FilesUploadMapper {

    List<FilesUpload> selectFiles(FilesUpload filesUpload);

    /**
     * 插入
     *
     * @param filesUpload
     * @return
     */
    int insert(FilesUpload filesUpload);

    /**
     * 删除
     *
     * @param fileId
     * @return
     */
    int delete(String fileId);
}
