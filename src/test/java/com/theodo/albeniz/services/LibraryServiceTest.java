package com.theodo.albeniz.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
                new Tune(UUID.fromString("534a2bce-2fb2-42ea-9aeb-044799289101"), "Thriller", "Michael J."),
                new Tune(UUID.fromString("2d156098-78ea-44ca-bb00-aef7060d9ee4"), "Uptown Funk", "Bruno M."),
                new Tune(UUID.fromString("8bb497fc-69a7-4faa-84c8-668490e9dd73"), "The Little Foam Man", "Patrick S."));

        assertEquals(
                mapper.writeValueAsString(expectedLibrary.stream().sorted(Comparator.comparing(Tune::getId)).toList()),
                mapper.writeValueAsString(
                        service.getAll(null).stream().sorted(Comparator.comparing(Tune::getId)).toList()));
    }

    @Test()
    @Description("it should return all tunes in library with title containing 'foam' ")
    void testGetAllWithQueryMethod() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        ObjectMapper mapper = new ObjectMapper();
        Collection<Tune> expectedTunes = List.of(
                new Tune(UUID.fromString("8bb497fc-69a7-4faa-84c8-668490e9dd73"), "The Little Foam Man", "Patrick S."));
        assertEquals(
                mapper.writeValueAsString(service.getAll("foam")),
                mapper.writeValueAsString(expectedTunes));
    }

    @Test()
    @Description("it should return tune with id 1")
    void testGetOneMethod() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        ObjectMapper mapper = new ObjectMapper();
        Tune expectedTune = new Tune(UUID.fromString("534a2bce-2fb2-42ea-9aeb-044799289101"), "Thriller", "Michael J.");
        assertEquals(
                mapper.writeValueAsString(service.getOne(UUID.fromString("534a2bce-2fb2-42ea-9aeb-044799289101"))),
                mapper.writeValueAsString(expectedTune));
    }

    @Test()
    @Description("it should have no effect on the inMemory library")
    void testAddTuneInMemory() throws Exception {
        LibraryService service = new InMemoryLibraryService();
        Collection<Tune> libraryBeforeAdd = service.getAll(null);
        service.addTune(new Tune(UUID.fromString("7e4d2b98-1c21-4de9-95e6-094b18eeb86a"), "Drown", "Three Days Grace"));
        Collection<Tune> libraryAfterAdd = service.getAll(null);
        assertEquals(libraryBeforeAdd.size(), libraryAfterAdd.size());
    }

    @Test()
    @Description("it should add one Tune to inDatabase library, with provided informations")
    void testAddTuneInDatabase() throws Exception {
        LibraryService service = new InDatabaseLibraryService();
        Tune newTune = new Tune(UUID.fromString("7e4d2b98-1c21-4de9-95e6-094b18eeb86a"), "Drown", "Three Days Grace");
        int librarySizeBeforeAdd = service.getAll(null).size();

        service.addTune(newTune);
        int librarySizeAfterAdd = service.getAll(null).size();
        assertEquals(librarySizeBeforeAdd + 1, librarySizeAfterAdd);
    }
}
