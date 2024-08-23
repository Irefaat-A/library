package com.sos.controllers;

import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.requests.ReportContaminateRequest;
import com.sos.models.requests.UpdateSurvivorLocationRequest;
import com.sos.models.responses.ReportContaminationResponse;
import com.sos.models.responses.SurvivorLocationUpdateResponse;
import com.sos.models.responses.SurvivorResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.services.SurvivorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/apocalypse/survivor", produces = MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SurvivorController {
    private final SurvivorService survivorService;
    public SurvivorController(final SurvivorService survivorService) {
        this.survivorService = survivorService;
    }

    @Operation(summary = "Add a survivor.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Successfully added survivor.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorResponse.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204",
                    description = "Robot provider service is unavailable.",
                    content = @Content) })
    @PostMapping
    public ResponseEntity<ApiResponse> createSurvivor(@RequestBody @Valid CreateSurvivorRequest createUserRequest) {
        ApiResponse apiResponse = survivorService.create(createUserRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update survivor location.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Successfully updated survivor location.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = SurvivorLocationUpdateResponse.class)) }),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204",
                    description = "Robot provider service is unavailable.",
                    content = @Content) })
    @PutMapping(path = "{id}")
    public ResponseEntity<ApiResponse> updateSurvivorLocation(@RequestBody @Valid UpdateSurvivorLocationRequest updateSurvivorLocationRequest, @PathVariable long id) {
        ApiResponse apiResponse = survivorService.updateLocation(id,updateSurvivorLocationRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Report a contaminated survivor.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Successfully submitted the contaminated requested.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportContaminationResponse.class))}),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200",
                    description = "Survivor infected please evacuate immediately!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReportContaminationResponse.class))})
    })
    @PutMapping("report/contamination")
    public ResponseEntity<ApiResponse> reportContaminateCase(@RequestBody @Valid ReportContaminateRequest reportContaminateRequest) {
        ApiResponse apiResponse = survivorService.reportContaminateCase(reportContaminateRequest);
        return ResponseEntity.ok(apiResponse);
    }
}
