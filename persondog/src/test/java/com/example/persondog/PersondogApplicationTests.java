package com.example.persondog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
class PersondogApplicationTests {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private CustodyRepository custodyRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testJPA() {

		Person person1 = Person.builder()
				.name("John")
				.age(30)
				.id("p-001")
				.build();

		Person person2 = Person.builder()
				.name("Jane")
				.age(25)
				.id("p-002")
				.build();

		Person person3 = Person.builder()
				.name("Bob")
				.age(35)
				.id("p-003")
				.build();

		Dog dog1 = Dog.builder()
				.name("Buddy")
				.breed("Golden Retriever")
				.age(2)
				.id("d-001")
				.build();

		Dog dog2 = Dog.builder()
				.name("Molly")
				.breed("German Shepherd")
				.age(3)
				.id("d-002")
				.build();

		Dog dog3 = Dog.builder()
				.name("Charlie")
				.breed("Labrador Retriever")
				.age(4)
				.id("d-003")
				.build();

		personRepository.save(person1);
		personRepository.save(person2);
		personRepository.save(person3);
		dogRepository.save(dog1);
		dogRepository.save(dog2);
		dogRepository.save(dog3);

		personRepository.findAll().forEach(System.out::println);
		dogRepository.findAll().forEach(System.out::println);
		custodyRepository.findAll().forEach(System.out::println);


		Custody custody1 = Custody.builder()
				.id("c-001")
				.dog(dog1)
				.person(person1)
				.startDate(new Date())
				.endDate(new Date())
				.contract("Contract 1")
				.status("Active")
				.isCurrent(true)
				.build();
		Custody custody2 = Custody.builder()
				.id("c-002")
				.dog(dog2)
				.person(person1)
				.startDate(new Date())
				.endDate(new Date())
				.contract("Contract 2")
				.status("Active")
				.isCurrent(true)
				.build();
		Custody custody3 = Custody.builder()
				.id("c-003")
				.dog(dog2)
				.person(person2)
				.startDate(new Date())
				.endDate(new Date())
				.contract("Contract 3")
				.status("Active")
				.isCurrent(true)
				.build();

		custodyRepository.save(custody1);
		custodyRepository.save(custody2);
		custodyRepository.save(custody3);

		// infinite recursion because of @ToString
		//custodyRepository.findAll().forEach(System.out::println);


		Optional<Custody> custody1t = custodyRepository.findById("c-001");
		if (custody1t.isPresent()) {
			System.out.println(custody1t.get().getContract());
		} else {
			System.out.println("Custody 001 not found");
		}
		Optional<Custody> custody2t = custodyRepository.findById("c-002");
		if (custody2t.isPresent()) {
			System.out.println(custody2t.get().getContract());
		} else {
			System.out.println("Custody 002 not found");
		}
		Optional<Custody> custody3t = custodyRepository.findById("c-003");
		if (custody3t.isPresent()) {
			System.out.println(custody3t.get().getContract());
		} else {
			System.out.println("Custody 003 not found");
		}

	}

	@Test
	void clearAll() {
		personRepository.deleteAll();
		dogRepository.deleteAll();
		custodyRepository.deleteAll();

	}

}
