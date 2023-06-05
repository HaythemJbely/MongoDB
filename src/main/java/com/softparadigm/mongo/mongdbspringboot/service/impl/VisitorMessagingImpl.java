package com.softparadigm.mongo.mongdbspringboot.service.impl;

import com.softparadigm.mongo.mongdbspringboot.domain.Bank;
import com.softparadigm.mongo.mongdbspringboot.domain.Client;
import com.softparadigm.mongo.mongdbspringboot.domain.Insurance;
import com.softparadigm.mongo.mongdbspringboot.domain.Restaurant;
import com.softparadigm.mongo.mongdbspringboot.service.Visitor;

import java.util.List;

public class VisitorMessagingImpl implements Visitor {

    public void sendMails(List<Client> clients) {
        for (Client client : clients) {
            client.accept(this);
        }
    }
    @Override
    public void visit(Bank bank) {
        System.out.println("Sending mail when visiting bank to : " + bank.getName());
    }

    @Override
    public void visit(Insurance insurance) {
        System.out.println("Sending mail when visiting insurance to : " + insurance.getName());
    }
}
