package com.sericulture.service;

import com.sericulture.model.entity.BaseEntity;
import com.sericulture.model.exceptions.ValidationException;
import com.sericulture.model.exceptions.ValidationMessage;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomValidator {

    @Autowired
    Validator validator;

    public <T extends BaseEntity> void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        violations.stream()
                        .map(cv -> new ValidationMessage(cv.getPropertyPath().toString(), cv.getMessage()))
                        .collect(Collectors.collectingAndThen(Collectors.toList(),
                        result -> {
                            if (!result.isEmpty()) throw new ValidationException(result);
                            return null;
                        }));
    }
}