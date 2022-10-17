package com.cmgzs.mapper;

import com.cmgzs.domain.TopicHot;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TopicHotMapper {
    int insert(TopicHot record);

    List<TopicHot> selectAll();
}