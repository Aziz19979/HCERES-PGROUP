package org.centrale.hceres.repository;

import org.centrale.hceres.items.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface NationalityRepository extends JpaRepository<Nationality, Integer> {


    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE  seq_nationality RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}