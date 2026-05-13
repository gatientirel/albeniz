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
        LibraryService service = new InMemoryLibraryServiceMock();
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
        LibraryService service = new InMemoryLibraryServiceMock();
        ObjectMapper mapper = new ObjectMapper();
        Collection<Tune> expectedTunes = List.of(new Tune(3, "The Little Foam Man", "Patrick S."));
        assertEquals(
                mapper.writeValueAsString(service.getAll("foam")),
                mapper.writeValueAsString(expectedTunes));
    }

    @Test()
    @Description("it should return tune with id 1")
    void testGetOneMethod() throws Exception {
        LibraryService service = new InMemoryLibraryServiceMock();
        ObjectMapper mapper = new ObjectMapper();
        Tune expectedTune = new Tune(1, "Thriller", "Michael J.");
        assertEquals(
                mapper.writeValueAsString(service.getOne(1)),
                mapper.writeValueAsString(expectedTune));
    }
}
