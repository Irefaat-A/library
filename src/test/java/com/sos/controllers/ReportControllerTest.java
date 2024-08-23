package com.sos.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sos.models.responses.RobotReportResponse;
import com.sos.models.responses.SurvivorSummaryResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import com.sos.services.ReportService;
import com.sos.services.RobotProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {ReportController.class})
public class ReportControllerTest {

    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private  RobotProviderService robotProviderService;
    @MockBean
    private ReportService reportService;


    @Test
    public void getAllRobots() throws Exception {
        ApiSuccessfulResponse response = new ApiSuccessfulResponse(RobotReportResponse
                .builder()
                .flyingRobotCount(1)
                .landRobotCount(2)
                .build(),
                "Successfully retrieved robot information.");

        when(reportService.allRobots()).thenReturn(response);

        this.mockMvc.perform(
                get("/v1/apocalypse/reports/robots")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(
                print()
        ).andExpect(result -> {
            ApiResponse apiResponse = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    ApiSuccessfulResponse.class
            );

            assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
            assertThat(apiResponse, hasProperty("message", equalTo("Successfully retrieved robot information.")));
        });
    }

    @Test
    public void getSummary() throws Exception {
        ApiSuccessfulResponse response = new ApiSuccessfulResponse(SurvivorSummaryResponse
                .builder()
                .totalSurvivors(10)
                .totalInfectedSurvivors(0)
                .totalNonInfectedSurvivors(10)
                .infectedSurvivorsPercentage(0.00F)
                .nonInfectedSurvivorsPercentage(100.0F)
                .infectSurvivors(new ArrayList<>())
                .nonInfectSurvivors(new ArrayList<>())
                .build(),
                "Successfully produced survivors report");

        when(reportService.getSurvivorSummary(true)).thenReturn(response);

        this.mockMvc.perform(
                get("/v1/apocalypse/reports/survivors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("infected","true")
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
            assertThat(apiResponse, hasProperty("message", equalTo("Successfully produced survivors report")));
        });
    }
}
