package com.theodo.albeniz.dto;

import java.util.UUID;

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
    private String title;

    @Getter()
    @Setter()
    private String author;
}
