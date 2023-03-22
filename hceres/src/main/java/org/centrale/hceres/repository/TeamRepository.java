package org.centrale.hceres.repository;

import org.centrale.hceres.items.Laboratory;
import org.centrale.hceres.items.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}