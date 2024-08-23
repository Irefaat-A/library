package com.sos.services;

import com.sos.entities.Survivor;
import com.sos.models.responses.SurvivorSummaryResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import com.sos.repositories.SurvivorRepository;
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
    public ApiResponse getSurvivorSummary(final boolean infected) {
        long totalSurvivors = this.survivorRepository.count();
        Optional<List<Survivor>> survivors = this.survivorRepository.findAllSurvivorsByInfectionState(infected);

        List<Survivor> allSurvivors = survivors.get();
        int searchedSurvivorsCount = survivors.get().isEmpty()? 0 : allSurvivors.size();
        float normalPercentage = allSurvivors.isEmpty()? 0 : (float) searchedSurvivorsCount/totalSurvivors;
        normalPercentage *= 100;

        SurvivorSummaryResponse survivorSummaryResponse = new SurvivorSummaryResponse();
        survivorSummaryResponse.setTotalSurvivors(totalSurvivors);
        int diff = Long.valueOf(totalSurvivors).intValue() - searchedSurvivorsCount;
        if (infected) {
            survivorSummaryResponse.setTotalNonInfectedSurvivors(diff);
            survivorSummaryResponse.setInfectSurvivors(allSurvivors);
            survivorSummaryResponse.setTotalInfectedSurvivors(searchedSurvivorsCount);
            survivorSummaryResponse.setInfectedSurvivorsPercentage(normalPercentage);
        } else {
            survivorSummaryResponse.setTotalInfectedSurvivors(diff);
            survivorSummaryResponse.setNonInfectSurvivors(allSurvivors);
            survivorSummaryResponse.setTotalNonInfectedSurvivors(searchedSurvivorsCount);
            survivorSummaryResponse.setNonInfectedSurvivorsPercentage(normalPercentage);
        }

        return new ApiSuccessfulResponse(survivorSummaryResponse, "Successfully produced survivors report");
    }
}
