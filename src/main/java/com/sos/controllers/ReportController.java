package com.sos.controllers;

import com.sos.models.responses.RobotReportResponse;
import com.sos.models.responses.SurvivorSummaryResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.services.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/apocalypse/reports", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class ReportController {
    private final ReportService reportService;

    @Autowired
    public ReportController(final ReportService reportService) {
        this.reportService = reportService;
    }

    @Operation(summary = "Get all robots.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Robot list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RobotReportResponse.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204",
                    description = "Robot provider service is unavailable.",
                    content = @Content) })
    @GetMapping(path = "robots")
    public ResponseEntity<ApiResponse> getAllRobots() {
        ApiResponse apiResponse = reportService.allRobots();
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Summary of all survivors.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Summary of survivors",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorSummaryResponse.class))
            })})
    @GetMapping(path = "survivors")
    public ResponseEntity<ApiResponse> getSummaryOfSurvivors(@RequestParam @Valid boolean infected) {
        ApiResponse apiResponse = reportService.getSurvivorSummary(infected);
        return ResponseEntity.ok(apiResponse);
    }
}
