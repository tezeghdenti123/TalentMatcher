package com.bezkoder.springjwt.repositories;

import com.bezkoder.springjwt.models.GestionnaireRH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GesionnaireRHRepository extends JpaRepository<GestionnaireRH,Long> {
}
