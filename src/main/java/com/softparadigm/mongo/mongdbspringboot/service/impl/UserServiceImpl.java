package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import com.softparadigm.mongo.mongdbspringboot.repository.UserRepository;
import com.softparadigm.mongo.mongdbspringboot.service.UserService;
import com.softparadigm.mongo.mongdbspringboot.strategyDesignPattern.CinValidationStrategy;
import com.softparadigm.mongo.mongdbspringboot.strategyDesignPattern.NameValidationStrategy;
import com.softparadigm.mongo.mongdbspringboot.strategyDesignPattern.UserValidationStrategy;
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

    /**
     * Validates a User object using different validation strategies.
     * @param user The User object to validate.
     * @return true if the User object is valid based on all validation strategies, false otherwise.
     */
    @Override
    public boolean validateUser(User user) {
        UserValidationStrategy validationId= new NameValidationStrategy();
        UserValidationStrategy validationName= new NameValidationStrategy();
        UserValidationStrategy validationCin= new CinValidationStrategy();

        boolean isValidId = validationId.validate(user);
        boolean isValidName = validationName.validate(user);
        boolean isValidCin = validationCin.validate(user);
        boolean isValid = isValidId && isValidName && isValidCin;
        return isValid;
    }

    /**
     * Saves a user to the repository.
     * This method saves a user to the repository. The user is marked as not validated by default.
     * @param user The user object to be saved.
     * @return The ID of the saved user.
     */
    @Override
    public String save(User user) {
        user.setValidated(false);
        return userRepository.save(user).getUserId();
    }

    /**
     * Retrieves users whose names start with a specified string.
     * This method queries the user repository to find users whose names start with the specified string.
     * @param name The string that the users names should start with.
     * @return A list of User objects representing the users whose names start with the specified string.
     */
    @Override
    public List<User> getUserStartWith(String name) {
        return userRepository.findByNameStartsWith(name);
    }

    /**
     * Deletes a user by their ID.
     * This method deletes a user from the repository based on their ID.
     * @param userId The ID of the user to be deleted.
     */
    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Retrieves users within a specified age range.
     * This method queries the user repository to find users whose age falls within the specified range.
     * @param minAge The minimum age of the users to include in the search.
     * @param maxAge The maximum age of the users to include in the search.
     * @return A list of User objects representing the users within the specified age range.
     */
    @Override
    public List<User> getByUserAge(Long minAge, Long maxAge) {
        return userRepository.findUserByAgeBetween(minAge,maxAge);
    }

    /**
     * This method performs a search query on the MongoDB collection to find users that match the provided criteria.
     * The search can be filtered by name, age range, and city. The results are returned as a pageable list of User objects.
     * @param name The name to search for. If null or empty, the name filter is not applied.
     * @param minAge The minimum age of the users to include in the search. If null, the age filter is not applied.
     * @param maxAge The maximum age of the users to include in the search. If null, the age filter is not applied.
     * @param city The city to search for. If null or empty, the city filter is not applied.
     * @param pageable The pageable object containing information about pagination and sorting.
     * @return A Page object containing the matching users based on the specified criteria.
     */
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

    /**
     * Retrieves the oldest user by city.
     * This method performs an aggregation query on the MongoDB collection to find the oldest user in each city.
     * @return A list of Document objects representing the oldest user in each city.
     */
    @Override
    public List<Document> getOldestUserByCity() {

        UnwindOperation unwindOperation = Aggregation.unwind("addresses");
        SortOperation sortOperation = Aggregation.sort(Sort.Direction.DESC,"age");
        GroupOperation groupOperation = Aggregation.group("addresses.city").first(Aggregation.ROOT).as("oldestUser");

        Aggregation aggregation = Aggregation.newAggregation(unwindOperation,sortOperation,groupOperation);

        List<Document> users = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();

        return users;
    }

    /**
     * Retrieves the population count by city.
     * This method performs an aggregation query on the MongoDB collection to calculate the population count in each city.
     * @return A list of Document objects representing the population count in each city.
     */
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

    /**
     * Retrieves users with matching skills.
     * This method performs an aggregation query on the MongoDB collection to find users who have the specified skill.
     * @param skill The skill to search for among the users.
     * @return A list of Document objects representing the users with matching skills.
     */
    @Override
    public List<Document> getUsersWithMatchingSkills(String skill) {
        MatchOperation matchOperation = Aggregation.match(Criteria.where("skills").in(skill));
        ProjectionOperation projectionOperation = Aggregation.project("name","skills").andExclude("_id");
        Aggregation aggregation = Aggregation.newAggregation(matchOperation,projectionOperation);
        List<Document> results = mongoTemplate.aggregate(aggregation, User.class, Document.class).getMappedResults();
        return results;
    }
}

