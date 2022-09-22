//package com.cmgzs.service.impl;
//
//
//import com.cmgzs.Tags.impl.DocumentClass;
//import com.cmgzs.constant.LatexFileNameConstant;
//import com.cmgzs.domain.Archive;
//import com.cmgzs.domain.ArchiveOption;
//import com.cmgzs.mapper.ArchiveMapper;
//import com.cmgzs.service.LaTexService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.ArrayList;
//
//@Service
//@Slf4j
//public class LaTexServiceImpl implements LaTexService {
//
//    @Resource
//    private ArchiveMapper documentMapper;
//    @Resource
//    private AuthUtils authUtils;
//
//    /**
//     * 项目工作文件位置
//     */
//    @Value(value = "${Thesis-Editor.workdir.path}")
//    private String path;
//
//    /**
//     * 将Document文档对象整合为代码段，保存编译。
//     *
//     * @param document 文档对象
//     * @return 代码段
//     */
//    @Override
//    public String convert(Archive document) throws IllegalAccessException {
//        // TODO: 2022/7/16
//        StringBuilder res = new StringBuilder();
//        //  解析排版配置参数
//        ArrayList<String> optionList = ArchiveOption.getOptionList(document.getOptions());
//        // 获取文档类型
//        String class_name = document.getType().getName();
//        // 文档头
//        res.append(new DocumentClass(optionList, class_name).getTagString());
//        // 解析包结构
//        for (UsePackage p : document.getPackages()) {
//            res.append(p.getTagString());
//        }
//        // 添加额外的配置
//        res.append("\\newcommand{\\tabincell}[2]{\\begin{tabular}{@{}#1@{}}#2\\end{tabular}}\n");
//
//        return res.toString();
//    }
//
//    /**
//     * 写入文件信息(格式——Tex)
//     *
//     * @param Id  项目id
//     * @param Tex 文件内容
//     * @return 操作是否成功
//     */
//    @Override
//    public File Save(String Id, String Tex) {
//        String authName = authUtils.getUserName();
//
//        /*工作目录地址拼接*/
//        String workDir = path + "/" + authName;
//        /*tex目录存储*/
//        String texWorkDir = workDir + "/" + Id + LatexFileNameConstant.FILE_SUFFIX;
//        File file = new File(texWorkDir, Id + ".tex");
//
//        BufferedWriter writer = null;
//        try {
//            writer = new BufferedWriter(new FileWriter(file));
//            writer.append(Tex);
//            writer.flush();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } finally {
//            try {
//                if (writer != null) {
//                    writer.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return file;
//    }
//
//    /**
//     * 编译Tex
//     *
//     * @param Id 文件名字
//     * @return 编译完成的PDF文件名称
//     */
//    @Override
//    public boolean Compile(String Id) {
//        //  执行编译
////        TermUtils.exeCmd("src/main/resources/scripts/latexmk.bat PDFS " + path + Id + "/" + Id);
//        return false;
//    }
//
//}
