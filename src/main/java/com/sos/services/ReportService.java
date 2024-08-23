package com.sos.services;

import com.sos.models.responses.api.ApiResponse;

public interface ReportService {
    ApiResponse allRobots();
    ApiResponse getSurvivorSummary(boolean infected);
}
