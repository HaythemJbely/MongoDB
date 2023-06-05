package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import com.softparadigm.mongo.mongdbspringboot.service.Visitor;
import lombok.Getter;


public abstract class Restaurant {
    public void orderBurger() {
        System.out.println("Ordering Burger...");
        Burger burger = createBurger();
        burger.prepare();
    }

    public abstract Burger createBurger();
}
