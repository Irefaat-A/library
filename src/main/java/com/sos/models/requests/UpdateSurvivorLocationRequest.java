package com.sos.models.requests;

import com.sos.models.Location;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UpdateSurvivorLocationRequest {
    @Valid
    private Location location;
}
