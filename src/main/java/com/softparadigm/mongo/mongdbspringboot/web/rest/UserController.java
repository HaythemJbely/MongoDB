package com.softparadigm.mongo.mongdbspringboot.web.rest;

import com.softparadigm.mongo.mongdbspringboot.domain.User;
import com.softparadigm.mongo.mongdbspringboot.repository.UserRepository;
import com.softparadigm.mongo.mongdbspringboot.service.UserService;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/validateUser/{userId}")
    public boolean validateUser(@PathVariable String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setValidated(userService.validateUser(user));
            userRepository.save(user);
            return userService.validateUser(user);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    @GetMapping("/getUserStartsWith")
    public List<User> getUserStartWith(@RequestParam("name") String name){
        return userService.getUserStartWith(name);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
    }

    @GetMapping("/age")
    public List<User> getByUserAge(@RequestParam Long minAge,
                                       @RequestParam Long maxAge) {
        return userService.getByUserAge(minAge,maxAge);
    }

    @GetMapping("/search")
    public Page<User> searchUser(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long minAge,
            @RequestParam(required = false) Long maxAge,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {
        Pageable pageable
                = PageRequest.of(page,size);
        return userService.search(name,minAge,maxAge,city,pageable);
    }

    @GetMapping("/oldestUser")
    public List<Document> getOldestUser() {
        return userService.getOldestUserByCity();
    }

    @GetMapping("/populationByCity")
    public List<Document> getPopulationByCity() {
        return userService.getPopulationByCity();
    }

    @GetMapping("/getUsersWithMatchingSkills")
    public List<Document> getUsersWithMatchingSkills(@RequestParam("skill") String skill) {
        return userService.getUsersWithMatchingSkills(skill);
    }

    @GetMapping("/updateFirstUser")
    public ResponseEntity<User> updateFirstUser(
            @RequestParam String oldName,
            @RequestParam String newName
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateFirst(oldName,newName));
    }

}
