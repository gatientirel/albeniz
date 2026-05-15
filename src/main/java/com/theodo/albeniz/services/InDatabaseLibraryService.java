package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.theodo.albeniz.config.ApplicationConfig;
import com.theodo.albeniz.dto.Tune;

import lombok.RequiredArgsConstructor;

@Service("inDatabaseLibraryService")
@Profile("!memory")
@RequiredArgsConstructor()
public class InDatabaseLibraryService implements LibraryService {
    private final Map<UUID, Tune> LIBRARY = new HashMap<UUID, Tune>();

    private final ApplicationConfig applicationConfig;

    @Override()
    public Collection<Tune> getAll(String query) {
        if (query == null || query.isBlank()) {
            return LIBRARY.values().stream()
                    .limit(applicationConfig.getApi().getMaxCollection())
                    .toList();
        }
        return LIBRARY.values().stream()
                .filter(tune -> tune.getTitle().toLowerCase().contains(query.toLowerCase()))
                .limit(applicationConfig.getApi().getMaxCollection())
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
    public boolean deleteTune(UUID id) {
        Tune deletedTune = LIBRARY.remove(id);
        return !deletedTune.equals(null);
    }

    @Override
    public boolean isExist(UUID id) {
        return LIBRARY.containsKey(id);
    }

    @Override
    public boolean modifyTune(Tune tune) {
        if (isExist(tune.getId())) {
            LIBRARY.put(tune.getId(), tune);
            return true;
        }
        return false;

    }

}
