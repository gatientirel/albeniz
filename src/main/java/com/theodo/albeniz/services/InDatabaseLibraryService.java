package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.theodo.albeniz.dto.Tune;

@Service("inDatabaseLibraryService")
@Profile("!memory")
public class InDatabaseLibraryService implements LibraryService {
    private final Map<UUID, Tune> LIBRARY = new HashMap<UUID, Tune>();

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
    public Tune getOne(UUID id) {
        return LIBRARY.get(id);
    }

    @Override()
    public UUID addTune(Tune tune) {
        tune.setId(UUID.randomUUID());
        LIBRARY.put(tune.getId(), tune);
        return tune.getId();
    }

    @Override
    public void deleteTune(UUID id) {
        LIBRARY.remove(id);
    }
}
