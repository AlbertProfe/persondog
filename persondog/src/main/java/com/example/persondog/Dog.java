package com.example.persondog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    // bi-directional, dog side is owner
    // many dogs can have one owner
    // so the annotation will be @ManyToOne
    // @JoinColumn is used to define the FK from Person
    @JsonIgnore
    @JoinColumn(name = "PERSON_FK")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Person personMapped;

}
