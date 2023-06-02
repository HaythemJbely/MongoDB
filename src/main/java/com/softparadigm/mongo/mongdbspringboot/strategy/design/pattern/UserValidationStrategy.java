package com.softparadigm.mongo.mongdbspringboot.strategy.design.pattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public interface UserValidationStrategy {
    boolean validate(User user);
}
