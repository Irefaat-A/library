package com.sos.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RobotInformation {
    private String model;
    private String serialNumber;
    private String manufacturedDate;
    private String category;
}
