package com.theodo.albeniz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodo.albeniz.dto.Tune;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

@WebMvcTest()
@AutoConfigureMockMvc()
public class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test()
    @Description("it should return the full library of music")
    public void testGetMusicsRoute() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Collection<Tune> expectedFullLibrary = List.of(
                new Tune(1, "Thriller", "Michael J."),
                new Tune(2, "Uptown Funk", "Bruno M."),
                new Tune(3, "The Little Foam Man", "Patrick S."));
        mockMvc.perform(get("/library/music")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        "[{\"id\": 1,\"title\": \"Thriller\",\"author\": \"Michael J.\"},"
                                + "{\"id\": 2,\"title\": \"Uptown Funk\",\"author\": \"Bruno M.\"},"
                                + "{\"id\": 3,\"title\": \"The Little Foam Man\",\"author\": \"Patrick S.\"}]"));
    }

    @Test()
    @Description("it should returns the information for an existing music")
    public void testGetOneMusic() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Tune expectedTune = new Tune(2, "Uptown Funk", "Bruno M.");
        mockMvc.perform(get("/library/music/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonMapper.writeValueAsString(expectedTune)));
    }

    @Test()
    @Description("it should return an empty JSON string for an non-existing music")
    public void testGetNotExistingMusic() throws Exception {
        MvcResult mvcResponse = mockMvc.perform(get("/library/music/14")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String mvcResponseContentStr = mvcResponse.getResponse().getContentAsString();
        assertEquals("", mvcResponseContentStr);
    }

    @Test()
    @Description("it should return musics where title contains 'up' (not case-sensitive) ")
    public void testGetMusicsWhereTitleContainsUp() throws Exception {
        ObjectMapper jsonMapper = new ObjectMapper();
        Collection<Tune> expectedTunes = List.of(new Tune(2, "Uptown Funk", "Bruno M."));
        MvcResult mvcResponse = mockMvc.perform(get("/library/music?title=up").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String mvcContentStr = mvcResponse.getResponse().getContentAsString();
        assertEquals(mvcContentStr, jsonMapper.writeValueAsString(expectedTunes));
    }
}
