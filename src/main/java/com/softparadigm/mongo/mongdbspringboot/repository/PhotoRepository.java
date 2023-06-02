package com.softparadigm.mongo.mongdbspringboot.repository;

import com.softparadigm.mongo.mongdbspringboot.domain.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo,String> {
}
