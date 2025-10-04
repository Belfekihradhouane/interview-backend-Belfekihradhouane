package fr.bred.example.interview.controllers;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void getCitiesWithNamePatternFiltersResults() throws Exception {
        mockMvc.perform(get("/api/cities").param("namePattern", "MAR*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", everyItem(startsWith("MAR"))));
    }

    @Test
    void getCitiesWithZipCodePatternFiltersResults() throws Exception {
        mockMvc.perform(get("/api/cities").param("zipCodePattern", "1013*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].zipCode", everyItem(startsWith("1013"))));
    }

    @Test
    void getCitiesWithNameAndZipCodePatternFiltersResults() throws Exception {
        mockMvc.perform(get("/api/cities")
                .param("namePattern", "MAR*")
                .param("zipCodePattern", "1013*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", everyItem(startsWith("MAR"))))
                .andExpect(jsonPath("$[*].zipCode", everyItem(startsWith("1013"))));
    }

    @Test
    void getCitiesWithLimitAndStart() throws Exception {
        mockMvc.perform(get("/api/cities")
                .param("_limit", "2")
                .param("_start", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    void getCitiesWithSortAsc() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cities")
                .param("_sort", "name")
                .param("_order", "asc"))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<String> names = JsonPath.read(json, "$[*].name");
        for (int i = 1; i < names.size(); i++) {
            assertTrue(names.get(i - 1).compareToIgnoreCase(names.get(i)) <= 0,
                "La liste n'est pas triée en ordre croissant : " + names.get(i - 1) + " > " + names.get(i));
        }
    }

    @Test
    void getCitiesWithSortDesc() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/cities")
                .param("_sort", "name")
                .param("_order", "desc"))
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<String> names = JsonPath.read(json, "$[*].name");
        for (int i = 1; i < names.size(); i++) {
            assertTrue(names.get(i - 1).compareToIgnoreCase(names.get(i)) >= 0,
                "La liste n'est pas triée en ordre décroissant : " + names.get(i - 1) + " < " + names.get(i));
        }
    }

    @Test
    void getCitiesWithNoResult() throws Exception {
        mockMvc.perform(get("/api/cities")
                .param("namePattern", "ZZZ*")
                .param("zipCodePattern", "9999*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(0)));
    }
}
