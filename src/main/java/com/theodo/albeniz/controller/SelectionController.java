package com.theodo.albeniz.controller;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.services.SelectionService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/selection")
@AllArgsConstructor
public class SelectionController {

    private final SelectionService selectionService;

    @GetMapping("")
    private ResponseEntity<List<Tune>> getSelection(Principal principal) {
        String userName = principal.getName();
        return ResponseEntity.ok(selectionService.getSelection(userName));
    }

    @PostMapping("/{tuneId}")
    private void addTitleToSelection(@PathVariable UUID tuneId, Principal principal) {
        selectionService.addToSelection(tuneId, principal.getName());
    }

    @DeleteMapping("/{tuneId}")
    private void deleteTuneFromSelection(@PathVariable UUID tuneId, Principal principal) {
        selectionService.removeFromSelection(tuneId, principal.getName());
    }

}
