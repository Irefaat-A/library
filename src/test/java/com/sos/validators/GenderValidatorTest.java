package com.sos.validators;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class GenderValidatorTest {
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void validGender(){
        GenderValidator genderValidator = new GenderValidator();

        boolean isValid = genderValidator.isValid("Male",constraintValidatorContext);
        Assert.assertTrue(isValid);

        isValid = genderValidator.isValid("male",constraintValidatorContext);
        Assert.assertTrue(isValid);
    }

    @Test
    public void invalidGender(){
        GenderValidator genderValidator = new GenderValidator();

        boolean isValid = genderValidator.isValid("CCC",constraintValidatorContext);
        Assert.assertFalse(isValid);
    }
}
