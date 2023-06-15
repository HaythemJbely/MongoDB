package com.softparadigm.mongo.mongdbspringboot.web.rest;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import com.softparadigm.mongo.mongdbspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQLUserController {
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @QueryMapping
    public User getUser(@Argument String id){
        return userRepository.findById(id).get();
    }

    @MutationMapping
    public User addUser(@Argument User user) {
        return userRepository.save(user);
    }
    @MutationMapping
    public void deleteUser(@Argument String id) {
        userRepository.deleteById(id);
    }
}
