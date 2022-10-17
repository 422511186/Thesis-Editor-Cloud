package com.cmgzs.mapper;

import com.cmgzs.domain.Topic;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TopicMapper {
    /**
     *
     * @param topicId
     * @return
     */
    int deleteByPrimaryKey(String topicId);

    int insert(Topic record);

    Topic selectByPrimaryKey(String topicId);

    List<Topic> selectAll(Topic topic);

    int updateByPrimaryKey(Topic record);

    int incrBrowse(String topicId);

    Topic selectUserIdByTopicId(String topicId);
}