package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import com.softparadigm.mongo.mongdbspringboot.service.impl.BeefBurgerImpl;

public class BeefBurgerRestaurant extends Restaurant{
    @Override
    public Burger createBurger() {
        System.out.println("Creating Beef Burger...");
        return new BeefBurgerImpl();    }
}
