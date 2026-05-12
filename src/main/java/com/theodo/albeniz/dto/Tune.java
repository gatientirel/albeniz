package com.theodo.albeniz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor()
public class Tune {
    @Getter()
    @Setter()
    private int id;

    @Getter()
    @Setter()
    private String title;

    @Getter()
    @Setter()
    private String author;
}
