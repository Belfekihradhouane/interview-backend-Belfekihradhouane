package fr.bred.example.interview.controllers;

import fr.bred.example.interview.models.City;
import fr.bred.example.interview.services.CityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
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
        log.info("getCities called with namePattern={}, zipCodePattern={}, limit={}, start={}, sort={}, order={}", namePattern, zipCodePattern, limit, start, sort, order);
        List<City> result = service.getCities(namePattern, zipCodePattern, limit, start, sort, order);
        log.info("getCities result size={}", result != null ? result.size() : 0);
        return result;
    }

    @GetMapping("/api/cities/nearest")
    public City findNearestCity(@RequestParam String x, @RequestParam String y) {
        log.info("findNearestCity called with x={}, y={}", x, y);
        City result = service.findNearestCity(x, y);
        log.info("findNearestCity result={}", result);
        return result;
    }
}
