package fr.bred.example.interview.controllers;

import fr.bred.example.interview.models.City;
import fr.bred.example.interview.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController implements CityControllerInterface {

    private final CityService service;
    public CityController(CityService service) {
        this.service = service;
    }

    @GetMapping("/api/cities")
    public List<City> getCities() {
        return service.getCities();
    }

    @GetMapping("/api/cities/search")
    public List<City> searchCities(
            @RequestParam(required = false) String namePattern,
            @RequestParam(required = false) String zipCodePattern) {
        return service.searchCities(namePattern, zipCodePattern);
    }

}
