package com.sos.services;

import com.sos.models.responses.api.ApiResponse;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;

@SpringBootTest(properties = "robot.provider.url=https://robotstakeover20210903110417.azurewebsites.net/robotcpu")
public class RobotProviderServiceImplTest {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RobotProviderService robotProviderService;

    @Test
    public void getAllRobots(){
        ApiResponse apiResponse = robotProviderService.allRobots();
        MatcherAssert.assertThat(apiResponse, hasProperty("status", equalTo(HttpStatus.OK)));
        MatcherAssert.assertThat(apiResponse, hasProperty("message", equalTo("Successfully retrieved robot information.")));
    }
}
