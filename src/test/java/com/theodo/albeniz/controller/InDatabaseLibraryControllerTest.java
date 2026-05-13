package com.theodo.albeniz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.dto.Tune;
import com.theodo.albeniz.services.InDatabaseLibraryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = LibraryController.class)
@AutoConfigureMockMvc()
@Import(value = { InDatabaseLibraryService.class })
@ActiveProfiles(profiles = "database")
public class InDatabaseLibraryControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Test()
        @DisplayName("it should add a new tune to the music library")
        public void testAddMusicRoute() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Tune tuneToAdd = new Tune(UUID.randomUUID(), "Hasta la vista", "PNL");

                MvcResult responseBeforeAdd = mockMvc
                                .perform(get("/library/music/" + tuneToAdd.getId())
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
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

        @Test()
        @DisplayName("it should add a tune to an empty library, then delete it")
        public void testDeleteTune() throws Exception {
                MvcResult getLibraryBeforeAddResult = mockMvc
                                .perform(get("/library/music").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String getLibraryBeforeAddContent = getLibraryBeforeAddResult.getResponse().getContentAsString();
                assertEquals("[]", getLibraryBeforeAddContent);

                ObjectMapper jsonMapper = new ObjectMapper();
                Tune newTune = new Tune(UUID.randomUUID(), "Holiday", "Green Day");
                MvcResult postAddTuneToLibraryResult = mockMvc
                                .perform(post("/library/music").content(jsonMapper.writeValueAsString(newTune))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String newlyAddedTuneId = postAddTuneToLibraryResult.getResponse().getContentAsString();
                MvcResult getLibraryAfterAddResult = mockMvc
                                .perform(get("/library/music").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String getLibraryAfterAddContent = getLibraryAfterAddResult.getResponse().getContentAsString();
                assertEquals(jsonMapper.writeValueAsString(
                                List.of(new Tune(UUID.fromString(newlyAddedTuneId), newTune.getTitle(),
                                                newTune.getAuthor()))),
                                getLibraryAfterAddContent);

                mockMvc.perform(delete("/library/music/" + newlyAddedTuneId).contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());
                MvcResult getLibraryAfterDeleteResult = mockMvc
                                .perform(get("/library/music").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk()).andReturn();
                String getLibraryAfterDeleteContent = getLibraryAfterDeleteResult.getResponse().getContentAsString();
                assertEquals("[]", getLibraryAfterDeleteContent);

        }

        @Test()
        @DisplayName("it should return a NOT_FOUND status")
        public void testGetInexistingMusic() throws Exception {
                mockMvc.perform(get("/library/music/" + UUID.randomUUID())).andExpect(status().isNotFound());
        }

        @Test()
        @DisplayName("it should return a NOT_FOUND status and a message in the body")
        public void testModifyInexistantMusic() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                mockMvc.perform(
                                put("/library/music")
                                                .content(jsonMapper.writeValueAsString(new Tune(UUID.randomUUID(),
                                                                "SLEEP WHEN IM DEAD", "Kami Kehoe")))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isNotFound())
                                .andReturn();
        }
}
