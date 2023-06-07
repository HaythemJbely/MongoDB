package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeefBurgerImpl implements Burger {
    private static final Logger logger = LoggerFactory.getLogger(BeefBurgerImpl.class);

    @Override
    public void prepare() {
        // Prepare Beef Burger
        logger.info("Preparing Beef Burger...");
    }
}
