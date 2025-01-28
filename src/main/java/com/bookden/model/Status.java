package com.bookden.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
public enum Status {
    AVAILABLE("Available"), BORROW("Borrow"), UNAVAILABLE("Unavailable");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public static Status getItemType(final String itemDescription) {
        return Status.valueOf(itemDescription.toUpperCase());
    }
}
