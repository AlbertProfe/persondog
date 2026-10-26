package com.example.persondog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PersondogApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersondogApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PersonRepository personRepository,
											   DogRepository dogRepository) {
		return args -> {
			Person person = Person.builder()
					.id("1")
					.name("John")
					.age(30)
					.build();

			personRepository.save(person);

			Dog dog = Dog.builder()
					.id("1")
					.name("Buddy")
					.breed("Golden Retriever")
					.age(5)
					.build();

			dogRepository.save(dog);

			System.out.println("Person saved: " + personRepository.findById("1"));
			System.out.println("Dog saved: " + dogRepository.findById("1"));
		};
	}

}
