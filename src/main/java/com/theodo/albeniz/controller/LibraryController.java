package com.theodo.albeniz.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.services.LibraryService;

import lombok.RequiredArgsConstructor;

@Qualifier("inMemoryLibraryService")
@RestController()
@RequestMapping("/library")
@RequiredArgsConstructor()
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/music")
    public Collection<Tune> getMusics(@RequestParam(required = false) String title) {
        return libraryService.getAll(title);
    }

    @GetMapping("/music/{id}")
    public Tune getMusic(@PathVariable() int id) {
        return libraryService.getOne(id);
    }
}