package com.sos.models.responses;

import com.sos.models.RobotInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RobotReportResponse {
    private int flyingRobotCount;
    private int landRobotCount;
    private List<RobotInformation> allFlyingRobots;
    private List<RobotInformation> allLandRobots;
}
