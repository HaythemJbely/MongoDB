package com.softparadigm.mongo.mongdbspringboot.domain;

import com.softparadigm.mongo.mongdbspringboot.service.Visitor;
import lombok.Data;

@Data
public abstract class Client {

    private final String name;
    private final String address;
    private final String number;

    public abstract void accept(Visitor visitor);

}
