package com.cmgzs.mapper;

import com.cmgzs.domain.CommentFirst;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
@Mapper
public interface CommentFirstMapper {
    /**
     * 通过主键删除记录行
     *
     * @param commentId 一级评论主键
     * @return 影响的行数
     */
    int deleteByPrimaryKey(String commentId);

    /**
     * 插入记录行
     *
     * @param record
     * @return
     */
    int insert(CommentFirst record);

    /**
     * 通过主键查询
     *
     * @param commentId
     * @return
     */
    CommentFirst selectByPrimaryKey(String commentId);

    /**
     * 查询列表
     *
     * @return
     */
    List<CommentFirst> selectAll();

    /**
     * 更新记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(CommentFirst record);
}