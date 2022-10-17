package com.cmgzs.mapper;

import com.cmgzs.domain.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {
    int deleteByPrimaryKey(String topicId);

    int insert(Topic record);

    Topic selectByPrimaryKey(String topicId);

    List<Topic> selectAll();

    int updateByPrimaryKey(Topic record);
}