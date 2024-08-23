package com.sos.models.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum ItemType {
    FOOD("Food"), WATER("Water"), MEDICATION("Medication"), AMMUNITION("Ammunition");

    private final String description;

    ItemType(String description) {
        this.description = description;
    }

    public static ItemType getItemType(final String itemDescription) {
        return ItemType.valueOf(itemDescription.toUpperCase());
    }

    public static boolean isValidItem(final String itemDescription) {
        Optional<ItemType> itemTypeEnum = Arrays.stream(ItemType.values())
                .filter(itemType -> itemType.name().equalsIgnoreCase(itemDescription))
                .findFirst();
        return itemTypeEnum.isPresent();
    }
}
