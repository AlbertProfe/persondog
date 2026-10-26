package com.example.persondog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<org.example.model.Person, String> {
}
