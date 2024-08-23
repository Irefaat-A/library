package com.sos.models.responses;

import com.sos.models.InventoryDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SurvivorResponse {
    private Long id;
    private String name;
    private String gender;
    private int age;
    private String identity;
    private boolean isInfected;
    private String updatedDate;
    private String createdDate;

    private Collection<InventoryDetail> inventoryDetails;
}
