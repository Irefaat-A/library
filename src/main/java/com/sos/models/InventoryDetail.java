package com.sos.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sos.entities.Inventory;
import com.sos.entities.Survivor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryDetail {
    private Long id;
    private String description;
    private String type;
    private int quantity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Survivor survivor;

    public static Collection<InventoryDetail> mapInventoryEntityToInventoryDetails(final Collection<Inventory> allInventory){
        return allInventory.stream().map(inventory  -> InventoryDetail
                .builder()
                .id(inventory.getId())
                .description(inventory.getDescription())
                .type(inventory.getType().getDescription())
                .quantity(inventory.getQuantity())
                .build())
                .collect(Collectors.toList());
    }
}
