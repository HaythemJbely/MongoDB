package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import com.softparadigm.mongo.mongdbspringboot.repository.UserRepository;
import com.softparadigm.mongo.mongdbspringboot.service.UserService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public String save(User user) {
        user.setValidated(false);
        return userRepository.save(user).getUserId();
    }

    @Override
    public List<User> getUserStartWith(String name) {
        return userRepository.findByNameStartsWith(name);
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public List<User> getByUserAge(Long minAge, Long maxAge) {
        return userRepository.findUserByAgeBetween(minAge,maxAge);
    }

    @Override
    public Page<User> search(String name, Long minAge, Long maxAge, String city, Pageable pageable) {

        Query query = new Query().with(pageable);
        List<Criteria> criteriaList = new ArrayList<>();

        if(name != null && !name.isEmpty()){
            criteriaList.add(Criteria.where("name").regex(name,"i"));
        }

        if(minAge != null && maxAge != null){
            criteriaList.add(Criteria.where("age").gte(minAge).lte(maxAge));
        }

        if(city != null && !city.isEmpty()){
            criteriaList.add(Criteria.where("addresses.city").is(city));
        }

        if(!criteriaList.isEmpty()){
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        Page<User> users = PageableExecutionUtils.getPage(
                mongoTemplate.find(query, User.class),pageable,()-> mongoTemplate.count(query.skip(0).limit(0), User.class)
        );

        return users;
    }

    @Override
    public List<Document> getOldestUserByCity() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestUser");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);

        List<Document> users = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();

        return users;
    }

    @Override
    public List<Document> getPopulationByCity() {
        UnwindOperation unwindOperation = Aggregation.unwind(("addresses"));
        GroupOperation groupOperation = Aggregation.group("addresses.city").count().as("populationCount");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"populationCount");

        ProjectionOperation projectionOperation = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("populationCount").as("population")
                .andExclude("_id");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation,projectionOperation);

        List<Document> documents = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();

        return documents;
    }

    @Override
    public List<Document> getUsersWithMatchingSkills(String skill) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("skills").in(skill));
        ProjectionOperation projectionOperation = Aggregation.project("name","skills").andExclude("_id");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,projectionOperation);
        List<Document> results = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();
        return results;
    }
}

