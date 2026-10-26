package com.example.persondog;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Person {

    @Id
    private String id;
    private String name;
    private int age;

}
