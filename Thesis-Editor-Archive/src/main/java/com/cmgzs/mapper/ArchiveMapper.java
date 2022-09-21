package com.cmgzs.mapper;


import com.cmgzs.domain.Archive;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ArchiveMapper extends MongoRepository<Archive, String> {
}
