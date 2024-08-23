package com.sos.validators;

import com.sos.models.enums.ItemType;
import com.sos.validators.annotations.ValidItemType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class ItemTypeValidator implements ConstraintValidator<ValidItemType, String> {
    @Override
    public void initialize(ValidItemType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (!StringUtils.hasText(value)){
            return false;
        }
        return ItemType.isValidItem(value);
    }
}
