package com.sos.services;

import com.sos.entities.Survivor;
import com.sos.entities.SurvivorLocation;
import com.sos.exceptions.BaseException;
import com.sos.models.Accused;
import com.sos.models.Item;
import com.sos.models.Location;
import com.sos.models.enums.Gender;
import com.sos.models.enums.ItemType;
import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.requests.ReportContaminateRequest;
import com.sos.models.responses.api.ApiResponse;
import com.sos.repositories.SurvivorRepository;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class SurvivorServiceImplTest {
    @Mock
    private SurvivorRepository survivorRepository;
    @InjectMocks
    private SurvivorServiceImpl survivorService;

    private CreateSurvivorRequest createSurvivorRequest = CreateSurvivorRequest
            .builder()
            .name("TTT")
            .age((byte) 1)
            .gender(Gender.MALE.getDescription())
            .identity("III")
            .location(Location.builder().latitude("asda").longitude("asda").build())
            .supplies(List.of(Item.builder().description("rrr").itemType(ItemType.MEDICATION.getDescription()).quantity(5).build()))
            .build();
    private Survivor newSurvivor = new Survivor(
            1L,
            createSurvivorRequest.getName(),
            createSurvivorRequest.getAge(),
            Gender.getGender(createSurvivorRequest.getGender()),
            createSurvivorRequest.getIdentity(),
            false,
            SurvivorLocation.builder().longitude(createSurvivorRequest.getLocation().getLatitude()).latitude(createSurvivorRequest.getLocation().getLongitude()).build(),
            new ArrayList<>(),
            new ArrayList<>(),
            new Date(),
            new Date());

    @Test
    public void createSurvivorSuccessFully(){
        Mockito
                .when(survivorRepository.findByNameAndAgeAndIdentity(createSurvivorRequest.getName(), createSurvivorRequest.getAge(), createSurvivorRequest.getIdentity()))
                .thenReturn(Optional.empty()).getMock();

        newSurvivor.addInventory(createSurvivorRequest.getSupplies());

        Mockito
                .when(survivorRepository.save(Mockito.any(Survivor.class)))
                .thenReturn(newSurvivor);

        ApiResponse apiResponse = survivorService.create(createSurvivorRequest);

        MatcherAssert.assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
        MatcherAssert.assertThat(apiResponse, hasProperty("message", equalTo("Successfully added survivor.")));
    }

    @Test
    public void createDuplicateSurvivor(){
        Mockito
                .when(survivorRepository.findByNameAndAgeAndIdentity(createSurvivorRequest.getName(), createSurvivorRequest.getAge(), createSurvivorRequest.getIdentity()))
                .thenReturn(Optional.of(newSurvivor)).getMock();

        assertThrows(BaseException.class,
                () -> survivorService.create(createSurvivorRequest),
                "Survivor already exist.");
    }

    @Test
    public void reportContaminateCaseSuccessfully(){
        Mockito
                .when(survivorRepository.findByNameAndGenderAndIdentity(createSurvivorRequest.getName(), Gender.getGender(createSurvivorRequest.getGender()), createSurvivorRequest.getIdentity()))
                .thenReturn(Optional.of(newSurvivor)).getMock();

        Mockito
                .when(survivorRepository.save(Mockito.any(Survivor.class)))
                .thenReturn(newSurvivor);

        ApiResponse apiResponse = survivorService.reportContaminateCase(ReportContaminateRequest
                .builder()
                .reporterIds(List.of(1L))
                .accused(Accused.builder().name(createSurvivorRequest.getName()).gender(createSurvivorRequest.getGender()).identity(createSurvivorRequest.getIdentity()).build())
                .build());

        MatcherAssert.assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
        MatcherAssert.assertThat(apiResponse, hasProperty("message", equalTo("Successfully submitted the contamination requested.")));
    }

    @Test
    public void reportContaminateCaseForNonExistingSurvivor(){
        Mockito
                .when(survivorRepository.findByNameAndGenderAndIdentity(createSurvivorRequest.getName(), Gender.getGender(createSurvivorRequest.getGender()), createSurvivorRequest.getIdentity()))
                .thenReturn(Optional.empty()).getMock();

        assertThrows(BaseException.class,
                () -> survivorService.reportContaminateCase(ReportContaminateRequest.builder().reporterIds(List.of(1L)).accused(Accused.builder().name(createSurvivorRequest.getName()).gender(createSurvivorRequest.getGender()).identity(createSurvivorRequest.getIdentity()).build()).build()),
                "Survivor not found {}.");
    }
}
