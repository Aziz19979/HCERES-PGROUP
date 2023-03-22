package org.centrale.hceres.repository;

import org.centrale.hceres.items.PhdType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface PhdTypeRepository extends JpaRepository<PhdType, Integer> {
    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE  seq_phd_type RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}