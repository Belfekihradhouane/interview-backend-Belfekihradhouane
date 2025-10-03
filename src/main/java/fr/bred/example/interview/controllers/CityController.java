package fr.bred.example.interview.controllers;

import fr.bred.example.interview.models.City;
import fr.bred.example.interview.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {

    private final CityService service;
    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping("/api/cities")
    public List<City> getCities() {
        return service.getCities();
    }
}

