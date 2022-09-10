package com.cmgzs.domain.document;

import com.cmgzs.domain.base.BaseEntity;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 文档排版配置参数
 */
@Data
public class DocumentOption extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 指定文本的字号，默认为10pt，可选为10pt,11pt,12pt
     */
    private String font_size;
    /**
     * 指定纸张大小，默认为letterpaper，可选为 a4paper，a5paper， b5paper， executivepaper 和 legalpaper
     */
    private String paper_size;
    /**
     * 指定标题，article默认为notitlepage，report和book默认为titlepage
     */
    private String titlepage;
    /**
     * 指定排版方向为横向，默认为纵向排版
     */
    private String scape;
    /**
     * 指定单栏排版，默认为onecolumn，可选为onecolumn，twocolumn
     */
    private String column;
    /**
     * 指定新的一章从奇数页开始，也就是右侧，可选为openright ，openany
     */
    private String openright;
    /**
     * 指定行间公式为左对齐，默认为居中对齐
     */
    private String fleqn;
    /**
     * 指定公式编号在左边，默认在右边
     */
    private String leqno;
    /**
     * 指定文稿模式(draft(草稿）、final(终稿))，草稿模式，断行不规则会在行尾添加黑色方块，默认为终稿模式
     */
    private String draft;
    /**
     * 指定使用开放式书目格式
     */
    private String openbib;
    /**
     * 指定论文的单双面模式，默认是单面印刷，可选为oneside,twoside
     */
    private String oneside;

    /**
     * 通过反射获取有效参数列表
     *
     * @param documentOption 文档排版配置参数对象
     * @return options 有效参数列表
     * @throws IllegalAccessException
     */
    public static ArrayList<String> getOptionList(DocumentOption documentOption) throws IllegalAccessException {
        ArrayList<String> options = new ArrayList<>();
        //对象判空
        if (documentOption != null) {
            for (Field field : documentOption.getClass().getDeclaredFields()) {
                //参数判空
                if (field.get(documentOption) != null)
                    options.add(String.valueOf(field.get(documentOption)));
            }
        }
        return options;
    }

    /**
     * 测试getOptionList方法
     *
     * @param args
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IllegalAccessException {
        DocumentOption option = new DocumentOption();
        option.setColumn("colum");
        option.setScape("setScape");
        option.setPaper_size("setPaper_size");
        ArrayList<String> list = DocumentOption.getOptionList(option);
        list.forEach(System.out::println);
    }
}
