package com.theodo.albeniz.controller;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/library")
public class LibraryController {

    @GetMapping("/music")
    public Collection<String> getMusic() {
        return Arrays.asList("Blip", "Blap");
    }
}