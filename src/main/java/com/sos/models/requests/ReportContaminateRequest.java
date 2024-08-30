package com.sos.models.requests;

import com.sos.models.Accused;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class ReportContaminateRequest {
    @Size(max = 10, min = 1)
    @UniqueElements
    private List<Long> reporterIds;
    @Valid
    private Accused accused;

    public byte totalReporters(){
        return (byte) reporterIds.size();
    }
}
