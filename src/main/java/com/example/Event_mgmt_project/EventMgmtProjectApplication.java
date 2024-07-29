package com.example.Event_mgmt_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class EventMgmtProjectApplication {

	public static void main(String[] args) {

		SpringApplication.run(EventMgmtProjectApplication.class, args);



		/*
		//Event event1= context.getBean(Event.class);
		Event event1= new Event();
		event1.setEventdate("23-Jul-24");
		event1.setDescription("Meeting");
		event1.setName("Wynk");
		event1.setLocation("floor 1");
		//Attendees att1= context.getBean(Attendees.class);
		Attendees att1 = new Attendees();
		att1.setName("Avni");
		att1.setEmail("b0317024@airtel.com");
		att1.setNum(9399652327L);

		System.out.println(event1.toString());
		System.out.println(att1.toString());
		*/


	}

}
