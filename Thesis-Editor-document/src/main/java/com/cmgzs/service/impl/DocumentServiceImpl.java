package com.cmgzs.service.impl;

import com.cmgzs.constant.LatexFileNameConstant;
import com.cmgzs.domain.Document;
import com.cmgzs.mapper.DocumentMapper;
import com.cmgzs.service.DocumentService;
import com.cmgzs.utils.AuthUtils;
import com.cmgzs.utils.FileUtils;
import com.cmgzs.utils.id.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DocumentServiceImpl implements DocumentService {

    @Resource
    private DocumentMapper documentMapper;

    @Resource
    private AuthUtils authUtils;

    /**
     * 项目工作文件位置
     */
    @Value(value = "${Thesis-Editor.workdir.path}")
    private String workdir_prefix;

    /**
     * 创建项目
     *
     * @param document 文档pojo
     * @return 结果
     */
    @Override
    public boolean createDocument(Document document) throws InterruptedException {

        String authName = authUtils.getUserName();

        /*工作目录地址拼接*/
        String workDir = workdir_prefix + "/" + authName;

        String uid = SnowFlakeUtil.getSnowFlakeId().toString();
        document.setUid(uid);
        document.setAuth(authName);

        documentMapper.insert(document);

        /*在物理磁盘上创建项目结构*/
        File file = new File(workDir, uid + LatexFileNameConstant.FILE_SUFFIX);

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
            Thread.sleep(500);
        }
        /*物理项目结构创建失败手动回滚*/
        documentMapper.deleteById(uid);
        return false;
    }

    /**
     * 删除项目
     *
     * @param uid 项目id
     * @return 结果
     */
    @Override
    public boolean deleteDocument(String uid) {
        String authName = authUtils.getUserName();
        /*工作目录地址拼接*/
        String workDir = workdir_prefix + "/" + authName;

        Document document = documentMapper.findById(uid).orElse(null);

        if (document == null)
            throw new RuntimeException("该项目不存在");

        String auth = document.getAuth();

        if (!authName.equals(auth))
            throw new RuntimeException("无权限对此项目进行删除操作");

        documentMapper.deleteById(uid);
        File file = new File(workDir, uid + LatexFileNameConstant.FILE_SUFFIX);

        if (file.exists()) {
            FileUtils.deleteFile(file);
        }

        return true;
    }

    /**
     * 通过项目id获取项目详细信息
     *
     * @param Id 文档项目id
     * @return 结果
     */
    @Override
    public Document getDocumentById(String Id) {

        String userName = authUtils.getUserName();

        Document document = new Document();
        document.setAuth(userName);
        document.setUid(Id);

        Example<Document> example = Example.of(document);
        return documentMapper.findOne(example).orElse(null);
    }

    /**
     * 获取全部的项目名称(当前用户)
     *
     * @return 结果
     */
    @Override
    public List<Document> getDocuments() {

        String userName = authUtils.getUserName();

        Document document = new Document();
        document.setAuth(userName);

        Example<Document> example = Example.of(document);
        List<Document> all = documentMapper.findAll(example);

        List<Document> result = new ArrayList<>();
        all.forEach(e -> {
            Document d = new Document();
            d.setUid(e.getUid());
            d.setName(e.getName());
            result.add(d);
        });
        return result;
    }

    /**
     * 修改文档对象
     *
     * @param documents 参数
     * @return 结果
     */
    @Override
    public int updateDocumentById(List<Document> documents) {
        return documentMapper.saveAll(documents).size();
    }

}
