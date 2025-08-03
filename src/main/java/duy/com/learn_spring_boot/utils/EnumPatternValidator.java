package duy.com.learn_spring_boot.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnumPatternValidator implements ConstraintValidator<EnumPattern, Enum<?>> {
    private Pattern pattern;
    @Override
    public void initialize(EnumPattern enumPattern) {
        try {
            this.pattern = Pattern.compile(enumPattern.regexp());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + enumPattern.regexp(), e);
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        Matcher matcher = pattern.matcher(value.toString());
        return matcher.matches();
    }
}
