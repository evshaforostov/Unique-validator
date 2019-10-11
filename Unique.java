package ru.zcts.travel.directory.location.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Created by evshaforostov on 16.11.2015.
 */
@Target( { TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueValidator.class)
public @interface Unique {

    String message() default "{Unique constraint violation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
