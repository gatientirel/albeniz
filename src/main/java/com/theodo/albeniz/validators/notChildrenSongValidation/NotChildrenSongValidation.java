package com.theodo.albeniz.validators.notChildrenSongValidation;

import com.theodo.albeniz.dto.Tune;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotChildrenSongValidation implements ConstraintValidator<NotAChildrenSong, Tune> {

    @Override
    public boolean isValid(Tune value, ConstraintValidatorContext context) {
        return !value.getAuthor().equals("Chantal G.");
    }

}
