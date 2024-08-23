package com.sos.models.requests;

import com.sos.models.Item;
import com.sos.models.Location;
import com.sos.validators.annotations.ValidGender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateSurvivorRequest {
    @NotBlank(message = "Name field must not be empty")
    private String name;
    @NotNull(message = "Age field must not be empty")
    private int age;
    @ValidGender()
    private String gender;
    @NotBlank(message = "Identity field must not be empty")
    private String identity;
    @Valid
    private Location location;
    @Valid()
    private Collection<Item> supplies;
}
