package com.bezkoder.springjwt.repositories;

import com.bezkoder.springjwt.models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffreRepository extends JpaRepository<Offre,Long> {
}
