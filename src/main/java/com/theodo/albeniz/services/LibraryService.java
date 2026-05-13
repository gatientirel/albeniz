package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.UUID;

import com.theodo.albeniz.dto.Tune;

public interface LibraryService {

    Collection<Tune> getAll(String query);

    Tune getOne(UUID id);

    UUID addTune(Tune tune);

    boolean deleteTune(UUID id);

    boolean isExist(UUID id);

    /**
     * Modifies an existing tune in the library.
     *
     * @param tune the tune to modify, matched by its ID
     * @return {@code true} if the tune existed and was successfully modified,
     *         {@code false} otherwise
     */
    boolean modifyTune(Tune tune);

}