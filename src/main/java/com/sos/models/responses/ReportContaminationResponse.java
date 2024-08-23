package com.sos.models.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportContaminationResponse {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String identity;
    private byte accusedCount;
    private boolean isInfected;
}
