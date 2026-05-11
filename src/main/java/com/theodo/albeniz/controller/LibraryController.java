package com.theodo.albeniz.controller;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.dto.Tune;

@RestController()
@RequestMapping("/library")
public class LibraryController {

    @GetMapping("/music")
    public Collection<Tune> getMusic() {
        return Arrays.asList(new Tune("Blip", "Zizou"), new Tune("Blap", "Schuman"));
    }
}