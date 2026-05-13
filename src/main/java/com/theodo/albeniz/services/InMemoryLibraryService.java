package com.theodo.albeniz.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.theodo.albeniz.dto.Tune;

@Service("inMemoryLibraryService")
@Profile("memory")
public class InMemoryLibraryService implements LibraryService {
    // NB: Service should be stateless, but here it simulates a database so let's
    // pretend it's okay
    private final static Map<UUID, Tune> LIBRARY = new HashMap<UUID, Tune>();

    static {
        // ADD static values (temporary)
        staticallyAddTune(new Tune(UUID.fromString("534a2bce-2fb2-42ea-9aeb-044799289101"), "Thriller", "Michael J."));
        staticallyAddTune(new Tune(UUID.fromString("2d156098-78ea-44ca-bb00-aef7060d9ee4"), "Uptown Funk", "Bruno M."));
        staticallyAddTune(
                new Tune(UUID.fromString("8bb497fc-69a7-4faa-84c8-668490e9dd73"), "The Little Foam Man", "Patrick S."));
    }

    private static void staticallyAddTune(Tune tune) {
        LIBRARY.put(tune.getId(), tune);
    }

    @Override
    public Collection<Tune> getAll(String query) {
        if (query == null || query.isBlank()) {
            return LIBRARY.values();
        }
        return LIBRARY.values().stream()
                .filter(tune -> tune.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }

    @Override
    public Tune getOne(UUID id) {
        return LIBRARY.get(id);
    }

    @Override
    public UUID addTune(Tune tune) {
        return null;
    }

    @Override
    public void deleteTune(UUID id) {
    }

}
