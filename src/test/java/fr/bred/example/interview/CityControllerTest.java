package fr.bred.example.interview;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void getCitiesLoadsFromResource() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").exists())   // attends que la ressource contienne au moins une ville avec "name"
                .andExpect(jsonPath("$[0].zipCode").exists());
    }

    @Test
    void getCitiesReturnsJsonFromResource() throws Exception {
        mockMvc.perform(get("/api/cities"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("MAROLLES SOUS LIGNIERES"))
                .andExpect(jsonPath("$[0].zipCode").value("10130"))
                .andExpect(jsonPath("$[1].name").value("LES GRANDES CHAPELLES"))
                .andExpect(jsonPath("$[1].zipCode").value("10170"));
    }
    @Test
    void getCitiesWithNamePatternFiltersResults() throws Exception {
        mockMvc.perform(get("/api/cities").param("namePattern", "MAR*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("MAROLLES SOUS LIGNIERES"))
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void getCitiesWithZipCodePatternFiltersResults() throws Exception {
        mockMvc.perform(get("/api/cities").param("zipCodePattern", "1013*"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].zipCode").value("10130"))
                .andExpect(jsonPath("$[1]").doesNotExist());
    }


}