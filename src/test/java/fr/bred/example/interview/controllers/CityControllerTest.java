package fr.bred.example.interview.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CityControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getCitiesReturnsJsonArray() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getCitiesReturnsAllCities() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].zipCode").exists());
    }

    @Test
    void searchCitiesByNamePattern() throws Exception {
        mockMvc.perform(get("/api/cities/search").param("namePattern", "MAR*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", everyItem(startsWith("MAR"))));
    }

    @Test
    void searchCitiesByZipCodePattern() throws Exception {
        mockMvc.perform(get("/api/cities/search").param("zipCodePattern", "1013*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].zipCode", everyItem(startsWith("1013"))));
    }

    @Test
    void searchCitiesByNameAndZipCodePattern() throws Exception {
        mockMvc.perform(get("/api/cities/search")
                .param("namePattern", "MAR*")
                .param("zipCodePattern", "1013*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("MAROLLES SOUS LIGNIERES")))
                .andExpect(jsonPath("$[0].zipCode", is("10130")))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void searchCitiesWithNoResult() throws Exception {
        mockMvc.perform(get("/api/cities/search")
                .param("namePattern", "ZZZ*")
                .param("zipCodePattern", "9999*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

}

