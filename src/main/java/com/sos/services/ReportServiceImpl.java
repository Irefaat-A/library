package com.sos.services;

import com.sos.entities.Survivor;
import com.sos.models.responses.SurvivorSummaryResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import com.sos.repositories.SurvivorRepository;
import com.sos.utils.PercentageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService{
    private final SurvivorRepository survivorRepository;
    private final RobotProviderService robotProviderService;

    @Autowired
    public ReportServiceImpl(SurvivorRepository survivorRepository, RobotProviderService robotProviderService) {
        this.survivorRepository = survivorRepository;
        this.robotProviderService = robotProviderService;
    }

    @Override
    public ApiResponse allRobots() {
        return robotProviderService.allRobots();
    }

    @Override
    public ApiResponse getSurvivorSummary(final boolean isInfected) {
        long totalNumberOfSurvivors = this.survivorRepository.count();
        Optional<List<Survivor>> searchResultsOfSurvivors = this.survivorRepository.findAllSurvivorsByInfectionState(isInfected);
        List<Survivor> allSurvivors = searchResultsOfSurvivors.get();

        int totalNumberOfSearchResults = allSurvivors.isEmpty()? 0 : allSurvivors.size();
        float percentage = PercentageCalculator.calculatePercentage(totalNumberOfSearchResults, totalNumberOfSurvivors);

        SurvivorSummaryResponse survivorSummaryResponse = new SurvivorSummaryResponse();
        survivorSummaryResponse.setTotalSurvivors(totalNumberOfSurvivors);
        int diff = Long.valueOf(totalNumberOfSurvivors).intValue() - totalNumberOfSearchResults;
        if (isInfected) {
            survivorSummaryResponse.setTotalNonInfectedSurvivors(diff);
            survivorSummaryResponse.setInfectSurvivors(allSurvivors);
            survivorSummaryResponse.setTotalInfectedSurvivors(totalNumberOfSearchResults);
            survivorSummaryResponse.setInfectedSurvivorsPercentage(percentage);
        } else {
            survivorSummaryResponse.setTotalInfectedSurvivors(diff);
            survivorSummaryResponse.setNonInfectSurvivors(allSurvivors);
            survivorSummaryResponse.setTotalNonInfectedSurvivors(totalNumberOfSearchResults);
            survivorSummaryResponse.setNonInfectedSurvivorsPercentage(percentage);
        }

        return new ApiSuccessfulResponse(survivorSummaryResponse, "Successfully produced searchResultsOfSurvivors report");
    }
}
