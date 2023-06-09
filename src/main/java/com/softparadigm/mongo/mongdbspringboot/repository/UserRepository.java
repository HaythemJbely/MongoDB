package com.softparadigm.mongo.mongdbspringboot.repository;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

    List<User> findByNameStartsWith(String name);

    List<User> findByAgeBetween(Long min , Long max);
    @Query(value="{ 'age' : { $gt : ?0, $lte : ?1 }}", fields="{addresses:0 , skills:0}")
    List<User> findUserByAgeBetween(Long minAge , Long maxAge);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}
