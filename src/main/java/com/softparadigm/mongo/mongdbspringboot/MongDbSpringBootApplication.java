package com.softparadigm.mongo.mongdbspringboot;

import com.softparadigm.mongo.mongdbspringboot.domain.Bank;
import com.softparadigm.mongo.mongdbspringboot.domain.Client;
import com.softparadigm.mongo.mongdbspringboot.domain.Restaurant;
import com.softparadigm.mongo.mongdbspringboot.service.impl.VisitorMessagingImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MongDbSpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(MongDbSpringBootApplication.class, args);

		List<Client>  clients = List.of(
				new Bank("bank_name", "bank_address", "bank_number"),
				new Restaurant("resto_name", "resto_address", "resto_number")
		);

		VisitorMessagingImpl visitor = new VisitorMessagingImpl();
		visitor.sendMails(clients); ;
	}

}
