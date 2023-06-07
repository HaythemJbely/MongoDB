package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Visitor;
import lombok.Getter;


@Getter
public class Bank extends Client{

    public Bank(String name, String address, String number) {
        super(name, address, number);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
