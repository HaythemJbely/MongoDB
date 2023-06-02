package com.softparadigm.mongo.mongdbspringboot.service;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    String save(User user);

    List<User> getUserStartWith(String name);

    void deleteUser(String userId);

    List<User> getByUserAge(Long minAge, Long maxAge);

    Page<User> search(String name, Long minAge, Long maxAge, String city, Pageable pageable);

    List<Document> getOldestUserByCity();

    List<Document> getPopulationByCity();

    List<Document> getUsersWithMatchingSkills(String skill);
}
