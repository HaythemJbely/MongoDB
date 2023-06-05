package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;

public class VeggieBurgerImpl implements Burger {
    @Override
    public void prepare() {
        // Prepare Veggie Burger
        System.out.println("Preparing Veggie Burger...");
    }
}
