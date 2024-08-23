package com.sos.services;

import com.sos.models.requests.CreateSurvivorRequest;
import com.sos.models.requests.ReportContaminateRequest;
import com.sos.models.requests.UpdateSurvivorLocationRequest;
import com.sos.models.responses.api.ApiResponse;

public interface SurvivorService {
    ApiResponse create(CreateSurvivorRequest createSurvivorRequest);
    ApiResponse updateLocation(Long id, UpdateSurvivorLocationRequest updateSurvivorLocationRequest);
    ApiResponse reportContaminateCase(ReportContaminateRequest reportContaminateRequest);
}
