package fr.bred.example.interview.services;


import fr.bred.example.interview.models.City;
import fr.bred.example.interview.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService implements CityServiceInterface {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository)
    { this.cityRepository = cityRepository; }

    public List<City> getCities() {
        return cityRepository.getCities(); }


    public List<City> searchCities(String namePattern, String zipPattern) {
        final String nameRegex = wildcardToRegex(namePattern == null ? "*" : namePattern);
        final String zipRegex  = wildcardToRegex(zipPattern == null ? "*" : zipPattern);

        return cityRepository.getCities().stream()
                .filter(c -> c.getName() != null && c.getName().toLowerCase().matches(nameRegex))
                .filter(c -> c.getZipCode() != null && c.getZipCode().toLowerCase().matches(zipRegex))
                .toList();
    }

    private String wildcardToRegex(String wildcard) {
        String s = wildcard.toLowerCase().replace(".", "\\.");
        s = s.replace("*", ".*");
        return "^" + s + "$";
    }


}
