package com.softparadigm.mongo.mongdbspringboot.strategydesignpattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public class UserIdValidationStrategy implements UserValidationStrategy{

    @Override
    public boolean validate(User user) {
        String userId = user.getUserId();
        return userId != null && userId.length() >= 5;
    }
}
