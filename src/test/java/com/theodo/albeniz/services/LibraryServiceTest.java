package com.theodo.albeniz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.dto.Tune;

public class LibraryServiceTest {

    @Test()
    @Description("it should return all tunes in library")
    void testGetAllMethod() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        ObjectMapper mapper = new ObjectMapper();
        Collection<Tune> expectedLibrary = List.of(
                new Tune(1, "Thriller", "Michael J."),
                new Tune(2, "Uptown Funk", "Bruno M."),
                new Tune(3, "The Little Foam Man", "Patrick S."));
        assertEquals(
                mapper.writeValueAsString(service.getAll(null)),
                mapper.writeValueAsString(expectedLibrary));
    }

    @Test()
    @Description("it should return all tunes in library with title containing 'foam' ")
    void testGetAllWithQueryMethod() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        ObjectMapper mapper = new ObjectMapper();
        Collection<Tune> expectedTunes = List.of(new Tune(3, "The Little Foam Man", "Patrick S."));
        assertEquals(
                mapper.writeValueAsString(service.getAll("foam")),
                mapper.writeValueAsString(expectedTunes));
    }

    @Test()
    @Description("it should return tune with id 1")
    void testGetOneMethod() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        ObjectMapper mapper = new ObjectMapper();
        Tune expectedTune = new Tune(1, "Thriller", "Michael J.");
        assertEquals(
                mapper.writeValueAsString(service.getOne(1)),
                mapper.writeValueAsString(expectedTune));
    }

    @Test()
    @Description("it should have no effect on the inMemory library")
    void testAddTuneInMemory() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        Collection<Tune> libraryBeforeAdd = service.getAll(null);
        service.addTune(new Tune(4, "Drown", "Three Days Grace"));
        Collection<Tune> libraryAfterAdd = service.getAll(null);
        assertEquals(libraryBeforeAdd.size(), libraryAfterAdd.size());
        assertEquals(null, service.getOne(4));
    }

    @Test()
    @Description("it should add one Tune to inDatabase library, with provided informations")
    void testAddTuneInDatabase() throws Exception {
        LibraryService service = new InDatabaseLibraryService();
        Tune newTune = new Tune(4, "Drown", "Three Days Grace");
        int librarySizeBeforeAdd = service.getAll(null).size();

        service.addTune(newTune);
        int librarySizeAfterAdd = service.getAll(null).size();
        assertEquals(librarySizeBeforeAdd + 1, librarySizeAfterAdd);

        ObjectMapper mapper = new ObjectMapper();
        Tune newlyAddedTune = service.getOne(newTune.getId());
        assertEquals(
                mapper.writeValueAsString(newlyAddedTune),
                mapper.writeValueAsString(service.getOne(newTune.getId())));
    }
}
