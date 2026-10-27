package com.example.persondog;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustodyRepository extends JpaRepository<Custody, String> {
}
