package fr.bred.example.interview.services;

import fr.bred.example.interview.models.City;
import fr.bred.example.interview.repositories.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {
    private CityRepository cityRepository;
    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityRepository = Mockito.mock(CityRepository.class);
        cityService = new CityService(cityRepository);
    }

    @Test
    void getCities_returnsAllCities() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities();
        assertEquals(2, result.size());
        assertEquals("Paris", result.get(0).getName());
        assertEquals("Lyon", result.get(1).getName());
    }

    @Test
    void searchCities_byNamePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.searchCities("Mar*", null);
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void searchCities_byZipCodePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.searchCities(null, "13*");
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void searchCities_byNameAndZipCodePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Marolles", "10130", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.searchCities("Mar*", "13*");
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void searchCities_noResult() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.searchCities("ZZZ*", "9999*");
        assertTrue(result.isEmpty());
    }
}
