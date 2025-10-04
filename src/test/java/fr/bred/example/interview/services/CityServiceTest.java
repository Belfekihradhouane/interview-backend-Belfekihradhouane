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

        List<City> result = cityService.getCities(null, null, null, null, null, null);
        assertEquals(2, result.size());
        assertEquals("Paris", result.get(0).getName());
        assertEquals("Lyon", result.get(1).getName());
    }

    @Test
    void getCities_byNamePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities("Mar*", null, null, null, null, null);
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void getCities_byZipCodePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities(null, "13*", null, null, null, null);
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void getCities_byNameAndZipCodePattern() {
        List<City> mockCities = Arrays.asList(
                new City("Marseille", "13000", null),
                new City("Marolles", "10130", null),
                new City("Paris", "75000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities("Mar*", "13*", null, null, null, null);
        assertEquals(1, result.size());
        assertEquals("Marseille", result.get(0).getName());
    }

    @Test
    void getCities_noResult() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities("ZZZ*", "9999*", null, null, null, null);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCities_withLimitAndStart() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null),
                new City("Marseille", "13000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities(null, null, 2, 1, null, null);
        assertEquals(2, result.size());
        assertEquals("Lyon", result.get(0).getName());
        assertEquals("Marseille", result.get(1).getName());
    }

    @Test
    void getCities_withSortAsc() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null),
                new City("Marseille", "13000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities(null, null, null, null, "name", "asc");
        assertEquals(3, result.size());
        assertEquals("Lyon", result.get(0).getName());
        assertEquals("Marseille", result.get(1).getName());
        assertEquals("Paris", result.get(2).getName());
    }

    @Test
    void getCities_withSortDesc() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null),
                new City("Marseille", "13000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities(null, null, null, null, "name", "desc");
        assertEquals(3, result.size());
        assertEquals("Paris", result.get(0).getName());
        assertEquals("Marseille", result.get(1).getName());
        assertEquals("Lyon", result.get(2).getName());
    }

    @Test
    void getCities_withInvalidLimitAndStart() {
        List<City> mockCities = Arrays.asList(
                new City("Paris", "75000", null),
                new City("Lyon", "69000", null)
        );
        Mockito.when(cityRepository.getCities()).thenReturn(mockCities);

        List<City> result = cityService.getCities(null, null, -1, -1, null, null);
        assertEquals(2, result.size()); // ignore les valeurs négatives
    }

    @Test
    void findNearestCity_shouldReturnNull() {
        CityService cityService = Mockito.mock(CityService.class, Mockito.CALLS_REAL_METHODS);
        City result = cityService.findNearestCity("1.0", "2.0");
        assertNull(result, "La méthode findNearestCity doit retourner null tant qu'elle n'est pas implémentée (TU KO)");
    }
}
