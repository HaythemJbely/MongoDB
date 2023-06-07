package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import com.softparadigm.mongo.mongdbspringboot.service.impl.BeefBurgerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeefBurgerRestaurant extends Restaurant{
    private static final Logger logger = LoggerFactory.getLogger(BeefBurgerRestaurant.class);

    @Override
    public Burger createBurger() {
        logger.info("Creating Beef Burger...");
        return new BeefBurgerImpl();    }
}
