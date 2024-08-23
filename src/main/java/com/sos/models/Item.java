package com.sos.models;

import com.sos.validators.annotations.ValidItemType;
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
public class Item {
    @NotBlank(message = "Description field must not be empty")
    private String description;
    @NotNull(message = "Item type field must not be empty")
    @ValidItemType()
    private String itemType;
    @NotNull(message = "Quantity field must not be empty")
    private int quantity;
}
