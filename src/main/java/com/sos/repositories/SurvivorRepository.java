package com.sos.repositories;

import com.sos.entities.Survivor;
import com.sos.models.enums.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {
    Optional<Survivor> findByNameAndAgeAndIdentity(final String name, int age, final String identity);
    Optional<Survivor> findByNameAndGenderAndIdentity(final String name, final Gender gender, final String identity);

    @Query(value = "select * from survivor where is_infected=:infected " ,nativeQuery = true)
    Optional<List<Survivor>> findAllSurvivorsByInfectionState(boolean infected);
}
