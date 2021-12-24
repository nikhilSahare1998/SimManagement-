package com.simmanagement.repository;

import com.simmanagement.entity.Sim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimRepository extends JpaRepository<Sim, Long> {
}
