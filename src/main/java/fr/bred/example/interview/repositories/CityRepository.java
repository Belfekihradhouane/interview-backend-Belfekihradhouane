package fr.bred.example.interview.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.bred.example.interview.models.City;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.List;

@Repository

public class CityRepository {
    private List<City> cities;

    @PostConstruct
    public void init() {
        try (InputStream is = getClass().getResourceAsStream("/cities.json")) {
            ObjectMapper m = new ObjectMapper();
            cities = m.readValue(is, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to load cities.json", e);
        }
    }

    public List<City> getCities() {
        return cities;
    }
}
