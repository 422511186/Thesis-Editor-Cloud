package com.cmgzs.utils;

import com.alibaba.fastjson.JSON;
import com.cmgzs.domain.Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文档数结构的相互转换
 * 扁平树 <==> 嵌套树
 *
 * @author huangzhenyu
 * @date 2022/9/23
 */
public class TreeUtil {

    /**
     * 集合转哈希表
     *
     * @param list 扁平结构
     * @return
     */
    private static HashMap<Integer, Content> list2Map(List<Content> list) {
        HashMap<Integer, Content> mapTable = new HashMap<>();
        mapTable.put(0, new Content("000", "顶层", 0, 0, new ArrayList<>(), null, null));
        for (Content content : list) {
            if (content.getChildren() == null) {
                content.setChildren(new ArrayList<>());
            }
            mapTable.put(content.getId(), content);
        }
        return mapTable;
    }

    /**
     * 扁平树 ==> 嵌套树
     *
     * @param list 扁平结构
     * @return 嵌套树形结构
     */
    public static Content generateTree(List<Content> list) {
        Map<Integer, Content> mapTable = list2Map(list);
        list.forEach(content -> {
            Content pidContent = mapTable.get(content.getPid());
            if (pidContent == null) {
                throw new IllegalArgumentException("数据存在错误,请检测数据");
            }
            List<Content> children = pidContent.getChildren();
            children.add(content);
        });
        return mapTable.get(0);
    }

    /**
     * 嵌套树 ==> 扁平树
     *
     * @param content Tree结构
     * @param result  结果
     */
    public static void tree2List(Content content, List<Content> result) {
        if (content == null)
            return;
        result.add(content);
        List<Content> children = content.getChildren();
        content.setChildren(null);
        if (children != null) {
            children.forEach(e -> tree2List(e, result));
        }
    }

    /**
     * 测试
     */
    /*public static void main(String[] args) {
        ArrayList<Content> contents = new ArrayList<Content>() {{
            add(new Content("1""1", 0, 1, null, null, null));
            add(new Content("2", 1, 2, null, null, null));
            add(new Content("3", 2, 3, null, null, null));
            add(new Content("4", 3, 4, null, null, null));
            add(new Content("5", 4, 5, null, null, null));
            add(new Content("6", 5, 6, null, null, null));
            add(new Content("7", 6, 7, null, null, null));
        }};
        Content content = generateTree(contents);
        String s = JSON.toJSONString(content);
        System.out.println(s);
        ArrayList<Content> result = new ArrayList<>();
        tree2List(content, result);
        result.forEach(System.out::println);
    }*/

}
