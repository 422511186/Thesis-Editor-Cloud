package com.cmgzs.mapper;

import com.cmgzs.domain.Content;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author huangzhenyu
 * @date 2022/9/24
 */
public interface ContentMapper extends MongoRepository<Content, String> {
}
