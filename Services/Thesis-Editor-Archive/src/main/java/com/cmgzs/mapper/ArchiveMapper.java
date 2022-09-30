package com.cmgzs.mapper;


import com.cmgzs.domain.Archive;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author huangzhenyu
 * @date 2022/9/24
 */
public interface ArchiveMapper extends MongoRepository<Archive, String> {
}
