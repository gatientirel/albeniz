package com.theodo.albeniz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.services.InMemoryLibraryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = LibraryController.class)
@AutoConfigureMockMvc()
@Import(value = { InMemoryLibraryService.class })
@ActiveProfiles(profiles = "memory")
public class LibraryControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Test()
        @Description("it should return the full library of music")
        public void testGetMusicsRoute() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Collection<Tune> expectedFullLibrary = List.of(
                                new Tune(UUID.fromString("534a2bce-2fb2-42ea-9aeb-044799289101"), "Thriller",
                                                "Michael J."),
                                new Tune(UUID.fromString("2d156098-78ea-44ca-bb00-aef7060d9ee4"), "Uptown Funk",
                                                "Bruno M."),
                                new Tune(UUID.fromString("8bb497fc-69a7-4faa-84c8-668490e9dd73"), "The Little Foam Man",
                                                "Patrick S."));
                mockMvc.perform(get("/library/music")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json(jsonMapper.writeValueAsString(expectedFullLibrary)));
        }

        @Test()
        @Description("it should returns the information for an existing music")
        public void testGetOneMusic() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Tune expectedTune = new Tune(UUID.fromString("2d156098-78ea-44ca-bb00-aef7060d9ee4"), "Uptown Funk",
                                "Bruno M.");
                mockMvc.perform(get("/library/music/" + expectedTune.getId())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(content().json(jsonMapper.writeValueAsString(expectedTune)));
        }

        @Test()
        @Description("it should return an empty JSON string for an non-existing music")
        public void testGetNotExistingMusic() throws Exception {
                MvcResult mvcResponse = mockMvc.perform(get("/library/music/" + UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String mvcResponseContentStr = mvcResponse.getResponse().getContentAsString();
                assertEquals("", mvcResponseContentStr);
        }

        @Test()
        @Description("it should return musics where title contains 'up' (not case-sensitive) ")
        public void testGetMusicsWhereTitleContainsUp() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Collection<Tune> expectedTunes = List.of(new Tune(
                                UUID.fromString("2d156098-78ea-44ca-bb00-aef7060d9ee4"), "Uptown Funk", "Bruno M."));
                MvcResult mvcResponse = mockMvc
                                .perform(get("/library/music?title=up").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String mvcContentStr = mvcResponse.getResponse().getContentAsString();
                assertEquals(mvcContentStr, jsonMapper.writeValueAsString(expectedTunes));
        }
}
