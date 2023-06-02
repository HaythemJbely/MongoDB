package com.softparadigm.mongo.mongdbspringboot.strategyDesignPattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public interface UserValidationStrategy {
    boolean validate(User user);
}
