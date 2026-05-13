package com.theodo.albeniz.services;

import java.util.Collection;

import com.theodo.albeniz.dto.Tune;

public interface LibraryService {

    Collection<Tune> getAll(String query);

    Tune getOne(int id);

    void addTune(Tune tune);

}