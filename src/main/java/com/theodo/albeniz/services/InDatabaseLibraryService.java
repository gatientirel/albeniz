package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.theodo.albeniz.dto.Tune;

@Service("inDatabaseLibraryService")
@Profile("!memory")
public class InDatabaseLibraryService implements LibraryService {
    private final Map<Integer, Tune> LIBRARY = new HashMap<Integer, Tune>();

    public InDatabaseLibraryService() {
        LIBRARY.put(1, new Tune(1, "Thriller", "Michael J."));
        LIBRARY.put(2, new Tune(2, "Uptown Funk", "Bruno M."));
        LIBRARY.put(3, new Tune(3, "The Little Foam Man", "Patrick S."));
    }

    @Override()
    public Collection<Tune> getAll(String query) {
        if (query == null || query.isBlank()) {
            return LIBRARY.values();
        }
        return LIBRARY.values().stream()
                .filter(tune -> tune.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    @Override()
    public Tune getOne(int id) {
        return LIBRARY.get(id);
    }

    @Override()
    public void addTune(Tune tune) {
        LIBRARY.put(tune.getId(), tune);
    }
}
