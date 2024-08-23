package com.sos.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class ItemTypeValidatorTest {
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void validItemTypes(){
        ItemTypeValidator itemTypeValidator = new ItemTypeValidator();

        boolean isValid = itemTypeValidator.isValid("Food",constraintValidatorContext);
        Assert.assertTrue(isValid);

        isValid = itemTypeValidator.isValid("Water",constraintValidatorContext);
        Assert.assertTrue(isValid);

        isValid = itemTypeValidator.isValid("Ammunition",constraintValidatorContext);
        Assert.assertTrue(isValid);
    }

    @Test
    public void invalidItemType(){
        ItemTypeValidator itemTypeValidator = new ItemTypeValidator();

        boolean isValid = itemTypeValidator.isValid("RRR",constraintValidatorContext);
        Assert.assertFalse(isValid);
    }
}
