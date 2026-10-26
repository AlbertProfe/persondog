package com.example.persondog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PersondogApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testJPA() {

		Person person = Person.builder()
			.id("p-001")
			.name("John")
			.age(30)
			.build();

		Dog dog1 = Dog.builder()
				.id("d-001")
				.name("Max").
				breed("Pastor").
				age(15).
				build();
		Dog dog2 = Dog.builder()
				.id("d-002")
				.name("Nano").
				breed("Pastor").
				age(6).
				build();
		Dog dog3 = Dog.builder()
				.id("d-003")
				.name("Max").
				breed("Timber").
				age(12).
				build();

		dog1.setPersonMapped(person);
		dog2.setPersonMapped(person);
		dog3.setPersonMapped(person);

		List<Dog> dogs = new ArrayList<>();
		dogs.add(dog1);
		dogs.add(dog2);
		dogs.add(dog3);
		person.setDogs(dogs);


		personRepository.save(person);

		person = personRepository.findById("p-001").get();
		System.out.println(person.getName());

	}

}
