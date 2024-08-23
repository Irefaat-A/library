package com.sos.models.enums;

import lombok.Getter;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Getter
public enum Gender {

    MALE("Male"),FEMALE("Female"),OTHER("Other");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public static Gender getGender(final String description) {
        return Gender.valueOf(description.toUpperCase());
    }

    public static boolean isValidGender(final String gender) {
        if (StringUtils.hasText(gender)) {
            return Arrays.stream(Gender.values()).anyMatch(gen -> gen.name().equalsIgnoreCase(gender));
        }else{
            return false;
        }
    }
}
