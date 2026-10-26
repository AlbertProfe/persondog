package com.example.persondog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersondogApplicationTests {

	//@Autowired
	//private PersonRepository personRepository;

	@Autowired
	private DogRepository dogRepository;

	@Test
	void contextLoads() {
	}


	@Test
	void testJPA() {

		Person person = Person.builder()
			.id("1")
			.name("John")
			.age(30)
			.build();

		Dog dog1 = Dog.builder()
			.id("1")
			.name("Buddy")
			.age(12)
				.breed("Pastor")
			.build();

		Dog dog2 = Dog.builder()
			.id("2")
			.name("Max")
			.age(8)
				.breed("Sephard")
			.build();

		Dog dog3 = Dog.builder()
			.id("3")
			.name("Nano")
			.age(3)
		    .breed("Pastor")
			.build();

		//person.setDog(dog);
		dog1.setPerson(person);
		dog2.setPerson(person);
		dog3.setPerson(person);

		dogRepository.save(dog1);
		dogRepository.save(dog2);
		dogRepository.save(dog3);

		dog1 = dogRepository.findById("1").get();
		System.out.println(dog1);
		dog2 = dogRepository.findById("2").get();
		System.out.println(dog2);
		dog3 = dogRepository.findById("3").get();
		System.out.println(dog3);



	}

}
