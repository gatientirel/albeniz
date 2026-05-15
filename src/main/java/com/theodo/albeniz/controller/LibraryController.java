package com.theodo.albeniz.controller;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.exceptions.NotFoundException;
import com.theodo.albeniz.services.LibraryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Qualifier("inMemoryLibraryService")
@RestController()
@RequestMapping("/library")
@RequiredArgsConstructor()
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/music")
    @Operation(summary = "Get all the musics", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public Collection<Tune> getMusics(@RequestParam(required = false) String title) {
        return libraryService.getAll(title);
    }

    @GetMapping("/music/{id}")
    @Operation(summary = "Get the tune given its id", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Incorrect input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tune does not exist")
    })
    public ResponseEntity<Tune> getMusic(@PathVariable() UUID id) {
        Tune tune = libraryService.getOne(id);
        if (tune == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tune);
    }

    @PostMapping("/music")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add a new Tune, and returns its ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Incorrect input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
    })
    public String addMusic(@Valid() @RequestBody(required = true) Tune tune) {
        return libraryService.addTune(tune).toString();
    }

    @DeleteMapping("music/{id}")
    @Operation(summary = "Delete a tune", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Incorrect input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tune does not exist")
    })
    public void deleteMusic(@PathVariable() UUID id) {
        libraryService.deleteTune(id);
    }

    @PutMapping("/music")
    @Operation(summary = "Modify a tune", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "Incorrect input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Tune does not exist")
    })
    @ResponseStatus(HttpStatus.OK)
    public void modifyMusic(@RequestBody(required = true) Tune tune) throws NotFoundException {
        boolean isModified = libraryService.modifyTune(tune);
        if (!isModified) {
            throw new NotFoundException();
        }
    }
}