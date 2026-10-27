package com.example.persondog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Dog {

    @Id
    private String id;
    private String name;
    private String breed;
    private int age;

    /**
     * Inverse side of the Person-Dog many-to-many.
     * The person_dog join table is owned by Person.dogs (mappedBy).
     */
    @ManyToMany(mappedBy = "dogs")
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Person> owners = new LinkedHashSet<>();

}
