package com.example.persondog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PersonDogManyToManyTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Person personWith(String id, String name) {
        return Person.builder().id(id).name(name).age(30).build();
    }

    private Dog dogWith(String id, String name, String breed) {
        return Dog.builder().id(id).name(name).breed(breed).age(4).build();
    }

    @Test
    void savingOwningSideCascadesToDogs() {
        Person ada = personWith("p1", "Ada");
        ada.addDog(dogWith("d1", "Rex", "Shepherd"));
        ada.addDog(dogWith("d2", "Bolt", "Husky"));

        personRepository.save(ada);
        entityManager.flush();
        entityManager.clear();

        assertThat(dogRepository.findAll())
                .extracting(Dog::getId)
                .containsExactlyInAnyOrder("d1", "d2");
    }

    @Test
    void associationSurvivesRoundTripOnBothSides() {
        Person ada = personWith("p1", "Ada");
        Dog rex = dogWith("d1", "Rex", "Shepherd");
        ada.addDog(rex);

        personRepository.save(ada);
        entityManager.flush();
        entityManager.clear();

        Person reloaded = personRepository.findById("p1").orElseThrow();
        assertThat(reloaded.getDogs())
                .extracting(Dog::getName)
                .containsExactly("Rex");

        Dog reloadedDog = dogRepository.findById("d1").orElseThrow();
        assertThat(reloadedDog.getOwners())
                .extracting(Person::getName)
                .containsExactly("Ada");
    }

    @Test
    void joinTableHoldsOneRowPerPersonDogPair() {
        Person ada = personWith("p1", "Ada");
        Person bob = personWith("p2", "Bob");
        Dog rex = dogWith("d1", "Rex", "Shepherd");

        ada.addDog(rex);
        bob.addDog(rex); // same dog, two owners

        // persist() seeds the whole transient graph in one pass.
        // save() would merge: the second merge reattaches rex while its
        // inverse 'owners' collection still references a transient bob,
        // causing ObjectRetrievalFailureException — a real M:N pitfall.
        entityManager.persist(ada);
        entityManager.persist(bob);
        entityManager.flush();

        Integer rows = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM person_dog", Integer.class);
        assertThat(rows).isEqualTo(2);
    }

    @Test
    void removeDogDetachesAssociationWithoutDeletingDog() {
        Person ada = personWith("p1", "Ada");
        Dog rex = dogWith("d1", "Rex", "Shepherd");
        ada.addDog(rex);

        personRepository.save(ada);
        entityManager.flush();

        Person managed = personRepository.findById("p1").orElseThrow();
        managed.removeDog(rex);
        entityManager.flush();
        entityManager.clear();

        assertThat(personRepository.findById("p1").orElseThrow().getDogs()).isEmpty();
        assertThat(dogRepository.findById("d1").orElseThrow().getOwners()).isEmpty();
        assertThat(dogRepository.findById("d1")).isPresent();
    }

    @Test
    void deletingPersonDoesNotDeleteItsDogs() {
        Person ada = personWith("p1", "Ada");
        Dog rex = dogWith("d1", "Rex", "Shepherd");
        ada.addDog(rex);

        personRepository.save(ada);
        entityManager.flush();

        personRepository.deleteById("p1");
        entityManager.flush();
        entityManager.clear();

        assertThat(personRepository.findById("p1")).isEmpty();
        assertThat(dogRepository.findById("d1")).isPresent();
        assertThat(jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM person_dog", Integer.class)).isZero();
    }
}
