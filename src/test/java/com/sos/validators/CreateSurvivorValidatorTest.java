package com.sos.validators;

import com.sos.entities.Survivor;
import com.sos.entities.SurvivorLocation;
import com.sos.exceptions.BaseException;
import com.sos.models.enums.Gender;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.validation.Errors;
import org.springframework.validation.SimpleErrors;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CreateSurvivorValidatorTest {
    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    public void duplicateSurvivor(){
        Survivor survivor = new Survivor(
                1L,
                "",
                (byte) 76,
                Gender.MALE,
                "ouoi",
                false,
                SurvivorLocation.builder().longitude("i").latitude("o").build(),
                new ArrayList<>(),
                new ArrayList<>(),
                new Date(),
                new Date());

        CreateSurvivorValidator createSurvivorValidator = new CreateSurvivorValidator();

        assertThrows(BaseException.class,
                () -> createSurvivorValidator.validate(Optional.of(survivor),null),
                "Survivor already exist.");
    }
}
