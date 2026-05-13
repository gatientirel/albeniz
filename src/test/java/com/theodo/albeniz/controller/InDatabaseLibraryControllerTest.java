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
import com.theodo.albeniz.services.InDatabaseLibraryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = LibraryController.class)
@AutoConfigureMockMvc()
@Import(value = { InDatabaseLibraryService.class })
@ActiveProfiles(profiles = "database")
public class InDatabaseLibraryControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Test()
        @Description("it should add a new tune to the music library")
        public void testAddMusicRoute() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Tune tuneToAdd = new Tune(UUID.randomUUID(), "Hasta la vista", "PNL");

                MvcResult responseBeforeAdd = mockMvc
                                .perform(get("/library/music/" + tuneToAdd.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
                String contentBeforeAdd = responseBeforeAdd.getResponse().getContentAsString();
                assertEquals("", contentBeforeAdd);

                MvcResult postResult = mockMvc.perform(
                                post("/library/music")
                                                .content(jsonMapper.writeValueAsString(tuneToAdd))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
                String addedId = postResult.getResponse().getContentAsString();

                MvcResult responseAfterAdd = mockMvc
                                .perform(get("/library/music/" + UUID.fromString(addedId))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
                String contentAfterAdd = responseAfterAdd.getResponse().getContentAsString();
                Tune expectedTune = new Tune(UUID.fromString(addedId), tuneToAdd.getTitle(), tuneToAdd.getAuthor());
                assertEquals(jsonMapper.writeValueAsString(expectedTune), contentAfterAdd);
        }
}
