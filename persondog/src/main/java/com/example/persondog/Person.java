package com.example.persondog;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Person {

    @Id
    private String id;
    private String name;
    private int age;

    /**
     * Owning side of the Person-Dog many-to-many.
     * Join table: person_dog(person_id, dog_id).
     * PERSIST/MERGE only — never REMOVE on a @ManyToMany (a dog may have other owners).
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "person_dog",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "dog_id")
    )
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Dog> dogs = new LinkedHashSet<>();

    // Bidirectional sync helpers — keep both sides of the association consistent
    public void addDog(Dog dog) {
        dogs.add(dog);
        dog.getOwners().add(this);
    }

    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.getOwners().remove(this);
    }

}
