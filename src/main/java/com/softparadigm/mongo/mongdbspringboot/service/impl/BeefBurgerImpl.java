package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;

public class BeefBurgerImpl implements Burger {
    @Override
    public void prepare() {
        // Prepare Beef Burger
        System.out.println("Preparing Beef Burger...");
    }
}
