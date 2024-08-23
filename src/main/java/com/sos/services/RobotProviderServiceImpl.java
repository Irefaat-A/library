package com.sos.services;

import com.sos.models.RobotInformation;
import com.sos.models.enums.RobotCategory;
import com.sos.models.responses.RobotReportResponse;
import com.sos.models.responses.api.ApiResponse;
import com.sos.models.responses.api.ApiSuccessfulResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class RobotProviderServiceImpl implements RobotProviderService{
    @Autowired
    private RestTemplate restTemplate;

    @Value("${robot.provider.url}")
    private String robotProviderUrl;

    @Override
    public ApiResponse allRobots() {
        ResponseEntity<RobotInformation[]> exchange = restTemplate.exchange(
                robotProviderUrl,
                HttpMethod.GET,
                null,
                RobotInformation[].class);

        List<RobotInformation> list = Arrays.stream(Objects.requireNonNull(exchange.getBody()))
                .sorted(Comparator.comparing(RobotInformation::getCategory))
                .toList();

        List<RobotInformation> allLandRobots = list.stream()
                .filter(robotInformation -> robotInformation.getCategory().equalsIgnoreCase(RobotCategory.LAND.getDescription()))
                .toList();

        List<RobotInformation> allFlyingRobots = list.stream()
                .filter(robotInformation -> robotInformation.getCategory().equalsIgnoreCase(RobotCategory.FLYING.getDescription()))
                .toList();

        return new ApiSuccessfulResponse(RobotReportResponse
                .builder()
                .flyingRobotCount(allFlyingRobots.size())
                .landRobotCount(allLandRobots.size())
                .allFlyingRobots(allFlyingRobots)
                .allLandRobots(allLandRobots)
                .build(),
                "Successfully retrieved robot information.");
    }
}
