package com.theodo.albeniz.validators.notChildrenSongValidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = NotChildrenSongValidation.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotAChildrenSong {
    String message() default "A Tune cannot be a children song";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
