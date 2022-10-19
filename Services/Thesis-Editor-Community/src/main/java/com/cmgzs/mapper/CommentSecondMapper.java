package com.cmgzs.mapper;

import com.cmgzs.domain.CommentSecond;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * @author huangzhenyu
 * @date 2022/10/19
 */
@Mapper
public interface CommentSecondMapper {


    int insert(CommentSecond record);

    CommentSecond selectByPrimaryKey(String commentId);

    List<CommentSecond> selectAll(String commentPid);

    int updateByPrimaryKey(CommentSecond record);

    /**
     * 通过对应的一级评论Id删除记录行
     *
     * @param commentPid
     * @return
     */
    int deleteByCommentPid(String commentPid);

    /**
     * 删除
     *
     * @param commentId 主键Id
     * @return
     */
    int deleteByPrimaryKey(String commentId);

}