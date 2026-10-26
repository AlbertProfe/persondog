package com.example.persondog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DogRepository extends JpaRepository<org.example.model.Dog, String> {
}
