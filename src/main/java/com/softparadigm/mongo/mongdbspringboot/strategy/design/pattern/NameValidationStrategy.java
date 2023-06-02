package com.softparadigm.mongo.mongdbspringboot.strategy.design.pattern;

import com.softparadigm.mongo.mongdbspringboot.domain.User;

public class NameValidationStrategy implements UserValidationStrategy{
    @Override
    public boolean validate(User user) {
        String name = user.getName();
        return name != null && name.startsWith("h");
    }
}
