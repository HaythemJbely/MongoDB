package com.softparadigm.mongo.mongdbspringboot;

import com.softparadigm.mongo.mongdbspringboot.domain.*;
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
				new Insurance("insurance_name", "insurance_address", "insurance_number")
		);

		System.out.println("==========================================");

		VisitorMessagingImpl visitor = new VisitorMessagingImpl();
		visitor.sendMails(clients);

		Restaurant beefRestaurant = new BeefBurgerRestaurant();
		beefRestaurant.orderBurger();

		System.out.println("==========================================");

		Restaurant veggieRestaurant = new VeggieBurgerRestaurant();
		veggieRestaurant.orderBurger();
	}

}
