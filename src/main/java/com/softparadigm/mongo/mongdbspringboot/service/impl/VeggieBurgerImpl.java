package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.service.Burger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VeggieBurgerImpl implements Burger {

    private static final Logger LOGGER = LoggerFactory.getLogger(VeggieBurgerImpl.class);
    @Override
    public void prepare() {
        // Prepare Veggie Burger
        LOGGER.info("Preparing Veggie Burger...");
    }
}
