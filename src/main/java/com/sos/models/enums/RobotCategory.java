package com.sos.models.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RobotCategory {

    FLYING("Flying"),LAND("Land");

    private final String description;

    RobotCategory(String description) {
        this.description = description;
    }
}
