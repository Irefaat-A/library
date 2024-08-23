package com.sos.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sos.entities.Survivor;
import com.sos.models.SurvivorDetail;
import com.sos.utils.DateTimeUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurvivorSummaryResponse {
    private long totalSurvivors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalInfectedSurvivors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalNonInfectedSurvivors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float infectedSurvivorsPercentage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float nonInfectedSurvivorsPercentage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SurvivorDetail> infectSurvivors;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<SurvivorDetail> nonInfectSurvivors;

    public void setInfectSurvivors(final List<Survivor> allSurvivors){
        this.infectSurvivors = getSurvivorDetailList(allSurvivors);
    }
    public void setNonInfectSurvivors(final List<Survivor> allSurvivors){
        this.nonInfectSurvivors = getSurvivorDetailList(allSurvivors);
    }

    private static List<SurvivorDetail> getSurvivorDetailList(final List<Survivor> allSurvivors) {
        List<SurvivorDetail> survivorDetails = allSurvivors.stream().map(survivor -> SurvivorDetail
                .builder()
                .id(survivor.getId())
                .name(survivor.getName())
                .gender(survivor.getGender().getDescription())
                .age(survivor.getAge())
                .identity(survivor.getIdentity())
                .isInfected(survivor.isInfected())
                .updatedDate(DateTimeUtil.formatLongTimestampToDate(survivor.getUpdatedDate()))
                .createdDate(DateTimeUtil.formatLongTimestampToDate(survivor.getCreatedDate()))
                .build()).toList();
        return survivorDetails;
    }
}
