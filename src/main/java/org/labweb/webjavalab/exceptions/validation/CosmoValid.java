package org.labweb.webjavalab.exceptions.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CosmoValidChecker.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CosmoValid {
    String message() default "Meaw***!! Your name must be alike to *cosmic* kittiesss!!!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
