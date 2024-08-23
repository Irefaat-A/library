package com.sos.validators.annotations;

import com.sos.validators.ItemTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ItemTypeValidator.class)
public @interface ValidItemType {
    String message() default "Item type field is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
