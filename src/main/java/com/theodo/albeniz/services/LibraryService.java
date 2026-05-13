package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.UUID;

import com.theodo.albeniz.dto.Tune;

public interface LibraryService {

    Collection<Tune> getAll(String query);

    Tune getOne(UUID id);

    UUID addTune(Tune tune);

    void deleteTune(UUID id);

}