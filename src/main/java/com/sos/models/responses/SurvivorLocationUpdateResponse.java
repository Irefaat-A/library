package com.sos.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SurvivorLocationUpdateResponse {
    private Long id;
    private String latitude;
    private String longitude;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updatedDate;
}
