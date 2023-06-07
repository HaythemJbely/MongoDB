package com.softparadigm.mongo.mongdbspringboot.strategydesignpattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public interface UserValidationStrategy {
    boolean validate(User user);
}
