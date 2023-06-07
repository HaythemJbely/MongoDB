package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.Bank;
import com.softparadigm.mongo.mongdbspringboot.domain.Client;
import com.softparadigm.mongo.mongdbspringboot.domain.Insurance;
import com.softparadigm.mongo.mongdbspringboot.service.Visitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VisitorMessagingImpl implements Visitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(VisitorMessagingImpl.class);

    public void sendMails(List<Client> clients) {
        for (Client client : clients) {
            client.accept(this);
        }
    }
    @Override
    public void visit(Bank bank) {
        LOGGER.info("Sending mail when visiting bank to : {}" , bank.getName());
    }

    @Override
    public void visit(Insurance insurance) {
        LOGGER.info("Sending mail when visiting insurance to : {}" , insurance.getName());
    }
}
