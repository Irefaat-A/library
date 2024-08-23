package com.sos.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Location {
    @NotBlank(message = "Latitude must not be empty")
    private String latitude;
    @NotBlank(message = "Longitude must not be empty")
    private String longitude;
}
