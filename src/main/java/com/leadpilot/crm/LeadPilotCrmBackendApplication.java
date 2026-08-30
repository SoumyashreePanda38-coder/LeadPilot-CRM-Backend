package com.leadpilot.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeadPilotCrmBackendApplication {

	public static void main(String[] args) {
		System.out.println("Starting LeadPilot CRM Backend Application...");
		SpringApplication.run(LeadPilotCrmBackendApplication.class, args);
	}

}
