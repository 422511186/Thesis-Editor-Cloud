package com.cmgzs.service.impl;

import com.cmgzs.constant.LatexFileNameConstant;
import com.cmgzs.domain.Archive;
import com.cmgzs.domain.UserContext;
import com.cmgzs.domain.base.PageResult;
import com.cmgzs.exception.CustomException;
import com.cmgzs.mapper.ArchiveMapper;
import com.cmgzs.service.ArchiveService;
import com.cmgzs.utils.FileUtils;
import com.cmgzs.utils.ServletUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RefreshScope
public class ArchiveServiceImpl implements ArchiveService {

    @Resource
    private ArchiveMapper archiveMapper;

    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * 项目工作文件位置
     */
    @Value(value = "${Thesis-Editor.workdir.path}")
    private String workdir_prefix;

    /**
     * 创建项目
     *
     * @param archive 文档pojo
     * @return 结果
     */
    @Override
    public boolean createDocument(Archive archive) throws InterruptedException {

        String authId = UserContext.getUserId();

        /*工作目录地址拼接*/
        String workDir = workdir_prefix + "/" + authId;

        String archiveId = SnowFlakeUtil.getSnowFlakeId().toString();
        archive.setArchiveId(archiveId);
        archive.setAuth(authId);
        archive.setCreateDateTime(new Date());

        archiveMapper.insert(archive);

        /*在物理磁盘上创建项目结构*/
        File file = new File(workDir, archiveId + LatexFileNameConstant.FILE_SUFFIX);

        /*最大尝试次数*/
        int maxCount = 3;
        for (int i = 0; i < maxCount; i++) {
            if (file.mkdirs()) {
                String filePath = file.getPath();
                new File(filePath, LatexFileNameConstant.DOCUMENT).mkdir();
                new File(filePath, LatexFileNameConstant.PDF).mkdir();
                new File(filePath, LatexFileNameConstant.DEPENDENCIES).mkdir();
                return true;
            }
            /*失败后等待500ms后重试*/
            Thread.sleep(300);
        }
        /*物理项目结构创建失败手动回滚*/
        archiveMapper.deleteById(archiveId);
        return false;
    }

    /**
     * 删除项目
     *
     * @param archiveId 文档id
     * @return 结果
     */
    @Override
    public boolean deleteDocument(String archiveId) {

        String authId = UserContext.getUserId();

        /*工作目录地址拼接*/
        String workDir = workdir_prefix + "/" + authId;

        Archive archive = archiveMapper.findById(archiveId).orElse(null);

        if (archive == null)
            throw new RuntimeException("该项目不存在");

        String auth = archive.getAuth();

        if (!authId.equals(auth))
            throw new RuntimeException("无权限对此项目进行删除操作");

        archiveMapper.deleteById(archiveId);
        File file = new File(workDir, archiveId + LatexFileNameConstant.FILE_SUFFIX);

        if (file.exists()) {
            FileUtils.deleteFile(file);
        }

        return true;
    }


    /**
     * 获取全部的文档(当前用户) (分页)
     *
     * @return 结果
     */
    @Override
    public Object getDocuments() {

        String authId = UserContext.getUserId();

        Integer pageNum = ServletUtils.getParameterToInt("pageNum");
        Integer pageSize = ServletUtils.getParameterToInt("pageSize");

        Criteria criteria = Criteria.where("auth").is(authId);

        Query query = new Query(criteria);

        long total = mongoTemplate.count(query, Archive.class);

        //分页
        if (pageNum != null && pageSize != null) {
            if (pageNum < 1) {
                pageNum = 1;
            }
            query.with(Sort.by("createDateTime").descending())
                    .limit(pageSize)
                    .skip((long) (pageNum - 1) * pageSize);
        }


        List<Archive> archives = mongoTemplate.find(query, Archive.class);

        return new PageResult<Archive>(pageNum, pageSize, total, archives);
    }

    /**
     * 修改文档信息
     *
     * @param archive 参数
     * @return 结果
     */
    @Override
    public int updateDocumentById(Archive archive) {
        archiveMapper.save(archive);
        return 1;
    }

    /**
     * 获取id对应文档的详细信息
     *
     * @param Id 文档项目id
     * @return 结果
     */
    @Override
    public Archive getDocumentById(String Id) {

        String authId = UserContext.getUserId();

        Archive archive = new Archive();
        archive.setArchiveId(Id);
        Example<Archive> example = Example.of(archive);

        Archive findOne = archiveMapper.findOne(example).orElse(null);

        if (findOne == null) {
            throw new CustomException("该文档不存在");
        }

        if (!findOne.getAuth().equals(authId)) {
            throw new CustomException("无权限");
        }

        return findOne;
    }
}
