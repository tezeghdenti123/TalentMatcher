package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.Formations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormationsRepository extends JpaRepository<Formations,Long> {
}
