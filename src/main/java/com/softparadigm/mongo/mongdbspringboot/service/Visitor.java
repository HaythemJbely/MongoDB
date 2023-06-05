package com.softparadigm.mongo.mongdbspringboot.service;

import com.softparadigm.mongo.mongdbspringboot.domain.Bank;
import com.softparadigm.mongo.mongdbspringboot.domain.Restaurant;

public interface Visitor {
    void visit(Bank bank);
    void visit(Restaurant restaurant);
}
