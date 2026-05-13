package com.theodo.albeniz.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
public class Tune {
    @Getter()
    @Setter()
    private UUID id;

    @Getter()
    @Setter()
    @NotBlank(message = "Title is mandatory")
    private String title;

    @Getter()
    @Setter()
    @NotBlank(message = "Auhtor is mandatory")
    private String author;
}
