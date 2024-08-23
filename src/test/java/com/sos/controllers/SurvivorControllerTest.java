package com.sos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.sos.models.Accused;
import com.sos.models.Item;
import com.sos.models.Location;
import com.sos.models.enums.Gender;
import com.sos.models.enums.ItemType;
import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.requests.ReportContaminateRequest;
import com.sos.models.requests.UpdateSurvivorLocationRequest;
import com.sos.models.responses.ReportContaminationResponse;
import com.sos.models.responses.SurvivorLocationUpdateResponse;
import com.sos.models.responses.SurvivorResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import com.sos.services.SurvivorService;
import com.sos.utils.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {SurvivorController.class})
public class SurvivorControllerTest {

    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SurvivorService survivorService;

    @Test
    public void createSurvivor() throws Exception {
        Date now = new Date();
        ApiSuccessfulResponse response = new ApiSuccessfulResponse(SurvivorResponse
                .builder()
                .id(1L)
                .name("TTT")
                .gender(Gender.MALE.getDescription())
                .age((byte) 1)
                .identity("III")
                .isInfected(false)
                .updatedDate(DateTimeUtil.formatLongTimestampToDate(now))
                .createdDate(DateTimeUtil.formatLongTimestampToDate(now))
                .build(),
                "Successfully added survivor.");

        CreateSurvivorRequest createSurvivorRequest = CreateSurvivorRequest
                .builder()
                .name("TTT")
                .age((byte) 1)
                .gender(Gender.MALE.getDescription())
                .identity("III")
                .location(Location.builder().latitude("asda").longitude("asda").build())
                .supplies(List.of(Item.builder().description("rrr").itemType(ItemType.MEDICATION.getDescription()).quantity(5).build()))
                .build();

        when(survivorService.create(any())).thenReturn(response);

        this.mockMvc.perform(
                post("/v1/apocalypse/survivor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSurvivorRequest))
        ).andDo(
                print()
        ).andExpect(result -> {
            ApiResponse apiResponse = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    ApiSuccessfulResponse.class
            );

            LinkedHashMap linkedHashMap = objectMapper.convertValue(apiResponse, LinkedHashMap.class);
            LinkedHashMap map = (LinkedHashMap) linkedHashMap.getOrDefault("data", null);

            assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
            assertThat(apiResponse, hasProperty("message", equalTo("Successfully added survivor.")));

            assertThat(map.getOrDefault("id",0L), equalTo(1));
            assertThat(map.getOrDefault("name",0L), equalTo("TTT"));
            assertThat(map.getOrDefault("age",0L), equalTo(1));
            assertThat(map.getOrDefault("gender",0L), equalTo(Gender.MALE.getDescription()));
        });
    }

    @Test
    public void createSurvivorWithNullName() {
        CreateSurvivorRequest createSurvivorRequest = CreateSurvivorRequest
                .builder()
                .name(null)
                .age((byte) 1)
                .gender(Gender.MALE.getDescription())
                .identity("III")
                .location(Location.builder().latitude("asda").longitude("asda").build())
                .supplies(List.of(Item.builder().description("rrr").itemType(ItemType.MEDICATION.getDescription()).quantity(5).build()))
                .build();


        assertThrows(UnrecognizedPropertyException.class,
                () -> this.mockMvc.perform(
                post("/v1/apocalypse/survivor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSurvivorRequest))
        ).andDo(
                print()
        ).andExpect(result -> {
            objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    ApiSuccessfulResponse.class
            );
        }),"");
    }

    @Test
    public void updateSurvivorLocation() throws Exception {
        Date now = new Date();
        ApiSuccessfulResponse response = new ApiSuccessfulResponse(SurvivorLocationUpdateResponse
                .builder()
                .id(1L)
                .longitude("asda")
                .latitude("asdda")
                .updatedDate(DateTimeUtil.formatLongTimestampToDate(now))
                .build(),
                "Successfully updated survivor location.");

        UpdateSurvivorLocationRequest updateSurvivorLocationRequest = UpdateSurvivorLocationRequest
                .builder()
                .location(Location.builder().longitude("asda").latitude("asdda").build())
                .build();

        when(survivorService.updateLocation(any(),any())).thenReturn(response);

        this.mockMvc.perform(
                put("/v1/apocalypse/survivor/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateSurvivorLocationRequest))
        ).andDo(
                print()
        ).andExpect(result -> {
            ApiResponse apiResponse = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    ApiSuccessfulResponse.class
            );

            LinkedHashMap linkedHashMap = objectMapper.convertValue(apiResponse, LinkedHashMap.class);
            LinkedHashMap map = (LinkedHashMap) linkedHashMap.getOrDefault("data", null);

            assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
            assertThat(apiResponse, hasProperty("message", equalTo("Successfully updated survivor location.")));

            assertThat(map.getOrDefault("id",0L), equalTo(1));
            assertThat(map.getOrDefault("longitude",""), equalTo("asda"));
            assertThat(map.getOrDefault("latitude",""), equalTo("asdda"));
        });
    }

    @Test
    public void reportContaminatedCase() throws Exception {
        ApiSuccessfulResponse response = new ApiSuccessfulResponse(ReportContaminationResponse
                .builder()
                .id(1L)
                .name("Deon")
                .gender("Male")
                .identity("TRD")
                .build(),
                "Successfully submitted the contaminated requested.");

        ReportContaminateRequest reportContaminateRequest = ReportContaminateRequest
                .builder()
                .reporterIds(List.of(1L))
                .accused(Accused.builder().name("Deon").gender("Male").identity("TRD").build())
                .build();

        when(survivorService.reportContaminateCase(any())).thenReturn(response);

        this.mockMvc.perform(
                put("/v1/apocalypse/survivor/report/contamination")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportContaminateRequest))
        ).andDo(
                print()
        ).andExpect(result -> {
            ApiResponse apiResponse = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    ApiSuccessfulResponse.class
            );

            LinkedHashMap linkedHashMap = objectMapper.convertValue(apiResponse, LinkedHashMap.class);
            LinkedHashMap map = (LinkedHashMap) linkedHashMap.getOrDefault("data", null);

            assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
            assertThat(apiResponse, hasProperty("message", equalTo("Successfully submitted the contaminated requested.")));

            assertThat(map.getOrDefault("id",0L), equalTo(1));
            assertThat(map.getOrDefault("name",""), equalTo("Deon"));
            assertThat(map.getOrDefault("gender",""), equalTo("Male"));
            assertThat(map.getOrDefault("identity",""), equalTo("TRD"));
        });
    }
}
