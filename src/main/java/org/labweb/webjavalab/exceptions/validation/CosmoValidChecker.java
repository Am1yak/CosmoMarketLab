package org.labweb.webjavalab.exceptions.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CosmoValidChecker implements ConstraintValidator<CosmoValid, String> {
    private final List<String> cosmoWords = Arrays.asList("cosmic", "star", "asteroid", "planet");

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        String lowerCatcase = s.toLowerCase();

        return cosmoWords.stream()
                .anyMatch(cosmos -> lowerCatcase.contains(cosmos));
    }
}
