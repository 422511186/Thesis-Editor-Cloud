package com.cmgzs.mapper;


import com.cmgzs.domain.document.Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocumentMapper extends MongoRepository<Document, String> {
}
