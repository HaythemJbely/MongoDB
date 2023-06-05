package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import com.softparadigm.mongo.mongdbspringboot.service.impl.VeggieBurgerImpl;

public class VeggieBurgerRestaurant extends Restaurant{
    @Override
    public Burger createBurger() {
        System.out.println("Creating Veggie Burger...");
        return new VeggieBurgerImpl();    }
}
