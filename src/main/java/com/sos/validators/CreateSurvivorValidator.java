package com.sos.validators;

import com.sos.entities.Survivor;
import com.sos.exceptions.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateSurvivorValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return CreateSurvivorValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Optional<Survivor> survivor = (Optional<Survivor>) target;
        if (survivor.isPresent()) {
            List<ObjectError> allErrors = new ArrayList<>();
            ObjectError objectError = new ObjectError("Survivor","Survivor already exist.");
            allErrors.add(objectError);
            throw new BaseException(HttpStatus.NOT_ACCEPTABLE,allErrors);
        }
    }
}
