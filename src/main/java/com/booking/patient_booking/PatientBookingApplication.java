package com.booking.patient_booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class })
public class PatientBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PatientBookingApplication.class, args);
	}

}
