package com.sos.repositories;

import com.sos.entities.SurvivorLocation;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

@DataJpaTest
public class SurvivorLocationRepositoryTest {

    @Autowired
    private SurvivorLocationRepository survivorLocationRepository;

    @Test
    public void addLocation(){
        SurvivorLocation newLocation = this.survivorLocationRepository.save(SurvivorLocation
                .builder()
                .latitude("aaaa")
                .longitude("sssss")
                .build());

        assertThat(newLocation, hasProperty("id", equalTo(1L)) );
        assertThat(newLocation, hasProperty("latitude", equalTo("aaaa")) );
        assertThat(newLocation, hasProperty("longitude", equalTo("sssss")) );
    }
}
