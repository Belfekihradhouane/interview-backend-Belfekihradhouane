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
    public List<City> getCities(
            @RequestParam(required = false) String namePattern,
            @RequestParam(required = false) String zipCodePattern,
            @RequestParam(name = "_limit", required = false) Integer limit,
            @RequestParam(name = "_start", required = false) Integer start,
            @RequestParam(name = "_sort", required = false) String sort,
            @RequestParam(name = "_order", required = false) String order
    ) {
        return service.getCities(namePattern, zipCodePattern, limit, start, sort, order);
    }
}
