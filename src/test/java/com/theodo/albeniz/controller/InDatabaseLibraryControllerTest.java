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
import com.theodo.albeniz.services.InMemoryLibraryService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

@WebMvcTest(controllers = LibraryController.class)
@AutoConfigureMockMvc()
@Import(value = { InDatabaseLibraryService.class })
@ActiveProfiles(profiles = "database")
public class InDatabaseLibraryControllerTest {
        @Autowired
        private MockMvc mockMvc;

        @Test()
        @Description("it should return the full library of music")
        public void testGetMusicsRoute() throws Exception {
                ObjectMapper jsonMapper = new ObjectMapper();
                Tune tuneToAdd = new Tune(4, "Hasta la vista", "PNL");

                MvcResult responseBeforeAdd = mockMvc
                                .perform(get("/library/music/4").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
                String contentBeforeAdd = responseBeforeAdd.getResponse().getContentAsString();
                assertEquals("", contentBeforeAdd);

                mockMvc.perform(
                                post("/library/music")
                                                .content(jsonMapper.writeValueAsString(tuneToAdd))
                                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk());

                MvcResult responseAfterAdd = mockMvc
                                .perform(get("/library/music/4").contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andReturn();
                String contentAfterAdd = responseAfterAdd.getResponse().getContentAsString();
                assertEquals(jsonMapper.writeValueAsString(tuneToAdd), contentAfterAdd);
        }
}
