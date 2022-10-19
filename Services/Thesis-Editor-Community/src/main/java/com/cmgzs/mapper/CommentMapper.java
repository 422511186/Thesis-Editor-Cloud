package com.cmgzs.mapper;

import com.cmgzs.domain.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author huangzhenyu
 * @date 2022/10/16
 */
@Mapper
public interface CommentMapper {

    /**
     * 查询记录
     *
     * @return
     */
    List<Comment> selectList(Comment comment);

    /**
     * 通过主键查询单条记录
     *
     * @param CommentId
     * @return
     */
    Comment selectByCommentId(String CommentId);

    /**
     * 插入单条数据
     *
     * @param comment
     * @return
     */
    int insertComment(Comment comment);

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    int insertCommentBatch(List<Comment> list);

    /**
     * 修改单条记录
     *
     * @param comment
     * @return
     */
    int updateComment(Comment comment);


    /**
     * 删除
     *
     * @param commentId
     * @return
     */
    int deleteComment(String commentId);


}
