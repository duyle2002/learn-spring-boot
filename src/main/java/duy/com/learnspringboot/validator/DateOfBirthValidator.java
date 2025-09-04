package duy.com.learnspringboot.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirthConstraint, LocalDate> {
    private int minAge;

    @Override
    public void initialize(DateOfBirthConstraint constraintAnnotation) {
        this.minAge = constraintAnnotation.min();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {
        if (Objects.isNull(value)) {
            return true;
        }
        long age = getAgeFromDateOfBirth(value);
        return age >= this.minAge;
    }

    private long getAgeFromDateOfBirth(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();

        return ChronoUnit.YEARS.between(dateOfBirth, now);
    }
}
