package com.sos.repositories;

import com.sos.entities.SurvivorLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurvivorLocationRepository extends JpaRepository<SurvivorLocation,Long> {}
