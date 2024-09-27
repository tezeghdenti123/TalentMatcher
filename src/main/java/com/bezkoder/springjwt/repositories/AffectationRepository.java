package com.bezkoder.springjwt.repositories;

import com.bezkoder.springjwt.models.Affectation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AffectationRepository extends JpaRepository<Affectation,Long> {
}
