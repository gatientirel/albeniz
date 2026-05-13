package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.theodo.albeniz.dto.Tune;

@Service("dataLibraryService")
@Profile("!memory")
public class DataLibraryService implements LibraryService {

    @Override
    public Collection<Tune> getAll(String query) {
        return Collections.emptyList();
    }

    @Override
    public Tune getOne(int id) {
        return null;
    }
}
