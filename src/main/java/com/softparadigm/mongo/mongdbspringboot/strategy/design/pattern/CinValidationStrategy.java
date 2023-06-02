package com.softparadigm.mongo.mongdbspringboot.strategy.design.pattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public class CinValidationStrategy implements UserValidationStrategy{
    @Override
    public boolean validate(User user) {
        Long cin = user.getCin();
        return cin != null && cin.equals(12345L);
    }
}
