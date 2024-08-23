package com.sos.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SurvivorDetail {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String identity;
    private boolean isInfected;
    private String updatedDate;
    private String createdDate;
}
