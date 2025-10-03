package fr.bred.example.interview.services;


import fr.bred.example.interview.models.City;
import fr.bred.example.interview.repositories.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {
    private final CityRepository cityRepository;
    public CityService(CityRepository cityRepository)
    { this.cityRepository = cityRepository; }

    public List<City> getCities() { return cityRepository.getCities(); }
}

